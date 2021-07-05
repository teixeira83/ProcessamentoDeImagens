import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.awt.*;

public class UpdatePicture implements PlugIn, DialogListener {

    ImagePlus originalImage;
    ImageProcessor originalProcessorCopy;

    @Override
    public void run(String s) {
        IJ.run("Clown");
        this.originalImage = IJ.getImage();
        this.originalProcessorCopy = this.originalImage.getProcessor().duplicate();

        apresentarInterfaceGrafica();
    }

    public void apresentarInterfaceGrafica() {
        GenericDialog interfaceGrafica = new GenericDialog("Ajustar Imagem");


        interfaceGrafica.addSlider("Configurar Brilho: ", -255, 255, 0, 1);
        interfaceGrafica.addSlider("Configurar Contraste: ", 0, 255, 0, 1);
        interfaceGrafica.addMessage("Configurar Solarização nos pixels com valor de...");
        interfaceGrafica.addSlider("No mínimo: ", 0, 255, 0, 1);
        interfaceGrafica.addSlider("No máximo: ", 0, 255, 0, 1);
        interfaceGrafica.addSlider("Configurar Desaturação: ", 0, 1, 1, 0.1);

        interfaceGrafica.addDialogListener(this);
        interfaceGrafica.showDialog();

        if (interfaceGrafica.wasCanceled()) {
            this.originalImage.setProcessor(this.originalProcessorCopy);
            this.originalImage.updateAndDraw();
            IJ.showMessage("O PlugIn foi cancelado e a imagem voltou a ter suas características originais!");
        } else {
            if (interfaceGrafica.wasOKed()) {
                IJ.showMessage("As características da imagem foram atualizadas com sucesso.");
            }
        }
    }

    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {

        int britnessSlider = (int) genericDialog.getNextNumber();
        int contrastSlider = (int) genericDialog.getNextNumber();
        int minSolarizationSlider = (int) genericDialog.getNextNumber();
        int maxSolarizationSlider = (int) genericDialog.getNextNumber();
        float desaturationSlider = (float) genericDialog.getNextNumber();

        int fatorDeContraste  = (259 * (contrastSlider + 255)) / (255 * (259 - contrastSlider));

        int pixels[] = {0,0,0};

        for(int i = 0; i < originalProcessorCopy.getWidth(); i++) {
            for(int j = 0; j < originalProcessorCopy.getHeight(); j++) {
                pixels = originalProcessorCopy.getPixel(i, j, pixels);

                for(int p = 0; p < pixels.length; p++) {
                    pixels[p] += britnessSlider;

                    pixels[p] = checkThePixelValue(pixels[p]);
                }
                this.originalImage.getProcessor().putPixel(i,j, pixels);
            }
        }

        for(int i = 0; i < originalProcessorCopy.getWidth(); i++) {
            for(int j = 0; j < originalProcessorCopy.getHeight(); j++) {
                pixels = this.originalImage.getProcessor().getPixel(i, j, pixels);

                for(int p = 0; p < pixels.length; p++) {
                    pixels[p] =  (fatorDeContraste * (pixels[p] - 128)) + 128;

                    pixels[p] = checkThePixelValue(pixels[p]);
                }
                this.originalImage.getProcessor().putPixel(i,j, pixels);
            }
        }


        for(int i = 0; i < originalProcessorCopy.getWidth(); i++) {
            for(int j = 0; j < originalProcessorCopy.getHeight(); j++) {
                pixels = this.originalImage.getProcessor().getPixel(i, j, pixels);

                for(int p = 0; p < pixels.length; p++) {
                    if ((pixels[p] > minSolarizationSlider) && (pixels[p] < maxSolarizationSlider)) {
                        pixels[p] = 255 - pixels[p];
                    }
                }
                this.originalImage.getProcessor().putPixel(i,j, pixels);
            }
        }


        for(int i = 0; i < originalProcessorCopy.getWidth(); i++) {
            for(int j = 0; j < originalProcessorCopy.getHeight(); j++) {
                pixels = this.originalImage.getProcessor().getPixel(i, j, pixels);
                int sum = pixels[0] + pixels[1] + pixels[2];
                int average = sum / 3;
                for(int p = 0; p < pixels.length; p++) {
                    pixels[p] = (int) (average + ( desaturationSlider * (pixels[p] - average)));
                }
                this.originalImage.getProcessor().putPixel(i,j, pixels);
            }
        }

        this.originalImage.updateAndDraw();
        return true;
    }

    private int checkThePixelValue(int pixel){
        if(pixel > 255) return 255;

        if(pixel < 0) return 0;

        return pixel;
    }

}

