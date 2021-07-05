import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.io.Opener;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.awt.*;

public class HistogramExpansionandEqualization implements PlugIn, DialogListener {

    int minValuePossible = 0;
    int maxValuePossible = 255;
    int minValueFound = 255;
    int maxValueFound = 0;

    @Override
    public void run(String s) {
        showGraphicInterface();
    }

    public void showGraphicInterface() {
        GenericDialog grapichInterface = new GenericDialog("Histogram: ");

        String[] methods = {"Expansão do Histograma", "Equalização do Histograma"};
        grapichInterface.addRadioButtonGroup("Escolha uma técnica: ", methods, 2, 1,"Expansão do Histograma");

        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if(grapichInterface.wasCanceled()) {
            IJ.showMessage("Histogram", "Menu encerrado com sucesso.");
        }
        if(grapichInterface.wasOKed()) {
            String method = grapichInterface.getNextRadioButton();
            createHistogram(method);
        }
    }

    private void createHistogram(String method) {
        Opener opener = new Opener();
        String imageFilePath = "/home/z3r0/Documentos/histograma.png";
        ImagePlus originalImage = opener.openImage(imageFilePath);
        originalImage.show();

        IJ.wait(1500);
        ImageProcessor originalProcessor = originalImage.getProcessor();
        checkPixelValues(originalProcessor);
        ImagePlus  histogramImage = IJ.createImage("Histograma", "8-bit", originalProcessor.getWidth(), originalProcessor.getHeight(), 1);
        ImageProcessor histogramProcessor = histogramImage.getProcessor();

        if(method == "Expansão do Histograma") {

            for(int i = 0; i < originalProcessor.getWidth(); i++){
                for(int j= 0; j < originalProcessor.getHeight(); j++){
                    int pixel = originalProcessor.getPixel(i, j);
                    int expansionPixel = minValuePossible + (pixel - minValueFound) * ((maxValuePossible - minValuePossible) / (maxValueFound - minValueFound));


                    histogramProcessor.putPixel(i,j, expansionPixel);
                }
            }
            histogramImage.show();
            histogramImage.updateAndDraw();

        }

        if(method == "Equalização do Histograma") {

            float intensities[] = new float[256];
            float intensitiesFound[] = new float[256];

            int numberOfPixels = 0;
            for(int i = 0; i < intensities.length; i++) {
                intensities[i] = i;
                intensitiesFound[i] = 0;
            }

            for(int i = 0; i < originalProcessor.getWidth(); i++){
                for(int j= 0; j < originalProcessor.getHeight(); j++){
                    int pixel = originalProcessor.getPixel(i,j);
                    intensitiesFound[pixel]++;
                    numberOfPixels++;
                }
            }

            float intensitiesProbability[] = new float[256];
            for(int i = 0; i < intensities.length; i++) {
                intensitiesProbability[i] = intensitiesFound[i] / numberOfPixels;
            }
            float cumulativyProbability[] = new float[256];

            cumulativyProbability[0] = intensitiesProbability[0];

            for(int i = 1; i < intensities.length; i++) {
                cumulativyProbability[i] = intensitiesProbability[i-1] + intensitiesProbability[i];
            }

            for(int i = 0; i < originalProcessor.getWidth(); i++){
                for(int j= 0; j < originalProcessor.getHeight(); j++){
                    int pixel = originalProcessor.getPixel(i,j);
                    int pixelWithNewRange = (int) cumulativyProbability[pixel] * 20;
                    histogramProcessor.putPixel(i,j, pixelWithNewRange);
                }
            }
            histogramImage.setProcessor(histogramProcessor);
            histogramImage.show();
        }
    }

    private void checkPixelValues(ImageProcessor processor) {
        for(int i = 0; i < processor.getWidth(); i++){
            for(int j= 0; j < processor.getHeight(); j++){
                int pixel = processor.getPixel(i, j);
                if( pixel > maxValueFound) {
                    maxValueFound = pixel;
                }
                if(pixel < minValueFound) {
                    minValueFound = pixel;
                }
            }
        }
    }


    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return true;
    }
}
