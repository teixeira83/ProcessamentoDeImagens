import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.List;

public class RGBToGreyScaleWithBookMethods implements PlugIn {

    private String chosenOption = "";
    private Boolean createNewImage = true;
    private List<Integer> averages = new ArrayList<>();

    public RGBToGreyScaleWithBookMethods(String chosenOption, Boolean createNewImage) {
        this.chosenOption = chosenOption;
        this.createNewImage = createNewImage;

    }
    @Override
    public void run(String s) {

        IJ.run("Clown");
        ImagePlus RGBImage = IJ.getImage();
        ImageProcessor RGBProcessor = RGBImage.getProcessor();
        int imageWidth = RGBProcessor.getWidth();
        int imageHeight = RGBProcessor.getHeight();
        List<Integer> redPixels = new ArrayList<>();
        List<Integer> greenPixels = new ArrayList<>();
        List<Integer> bluePixels = new ArrayList<>();

        int pixels[] = {0,0,0};
        int sum = 0;
        for(int i = 0; i < imageWidth; i++) {
            for(int j = 0; j < imageHeight; j++) {
                pixels = RGBProcessor.getPixel(i,j,pixels);
                redPixels.add(pixels[0]);
                greenPixels.add(pixels[1]);
                bluePixels.add(pixels[2]);

                sum = pixels[0] + pixels[1] + pixels[2];
                averages.add(sum / 3);
            }
        }

        if(this.chosenOption == "Média dos Canais RGB") {
            if(createNewImage) {

                ImagePlus averageImage = IJ.createImage("Escala de Cinza Pela Média", "8-bit", imageWidth, imageHeight, 1);
                ImageProcessor averageProcessor = averageImage.getProcessor();
                int k = 0;
                for(int i = 0; i < averageProcessor.getWidth(); i++) {
                    for(int j = 0; j < averageProcessor.getHeight(); j++) {
                        averageProcessor.putPixel(i, j, averages.get(k));
                        k++;
                    }
                }
                averageImage.show();
            } else {
                IJ.run("8-bit");
            }
        }
        if(this.chosenOption == "Luminância Analógica") {
            if(createNewImage) {
                ImagePlus luminAnalogImage = IJ.createImage("Escala de Cinza Luminância Analógica", "8-bit", imageWidth, imageHeight, 1);
                ImageProcessor luminAnalogProcessor = luminAnalogImage.getProcessor();

                int k = 0;
                Double wR = 0.299;
                Double wG = 0.587;
                Double wB = 0.114;
                for(int i = 0; i < luminAnalogProcessor.getWidth(); i++) {
                    for(int j = 0; j < luminAnalogProcessor.getHeight(); j++) {
                        int pixel = ( int ) ((wR * redPixels.get(k)) + (wG * greenPixels.get(k)) + (wB * bluePixels.get(k)));
                        luminAnalogProcessor.putPixel(i, j, pixel);
                        k++;
                    }
                }
                luminAnalogImage.show();
            } else {
                IJ.run("8-bit");
            }
        }
        if(this.chosenOption == "Luminância Digital") {
            if(createNewImage) {
                ImagePlus luminDigitalImage = IJ.createImage("Escala de Cinza Luminância Digital", "8-bit", imageWidth, imageHeight, 1);
                ImageProcessor luminDigitalProcessor = luminDigitalImage.getProcessor();

                int k = 0;
                Double wR = 0.2125;
                Double wG = 0.7154;
                Double wB = 0.072;

                for(int i = 0; i < luminDigitalProcessor.getWidth(); i++) {
                    for(int j = 0; j < luminDigitalProcessor.getHeight(); j++) {
                        int pixel = ( int ) ((wR * redPixels.get(k)) + (wG * greenPixels.get(k)) + (wB * bluePixels.get(k)));
                        luminDigitalProcessor.putPixel(i, j, pixel);
                        k++;
                    }
                }
                luminDigitalImage.show();
            } else {
                IJ.run("8-bit");
            }
        }
    }

}
