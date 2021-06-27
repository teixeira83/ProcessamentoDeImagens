import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;

import java.util.ArrayList;
import java.util.List;

public class ReverseThreeGreyScaleToRGB implements PlugIn {
    @Override
    public void run(String s) {
        List<Integer> redPixels = new ArrayList<>();
        List<Integer> greenPixels = new ArrayList<>();
        List<Integer> bluePixels = new ArrayList<>();

        ImagePlus redImage = getOpenImageByTitle("Imagem Vermelha");
        ImagePlus greenImage = getOpenImageByTitle("Imagem Verde");
        ImagePlus blueImage = getOpenImageByTitle("Imagem Azul");

        redPixels = getPixelsFrom8BitImage(redImage, redPixels);
        greenPixels = getPixelsFrom8BitImage(greenImage, greenPixels);
        bluePixels = getPixelsFrom8BitImage(blueImage, bluePixels);

        int widthReversedRGBImage = redImage.getWidth();
        int heightReversedRGBImage = redImage.getHeight();
        ImagePlus reversedRGBImage = IJ.createImage("RGB Revertid", "RGB",widthReversedRGBImage, heightReversedRGBImage, 3, 0,0);

        int pixels[] = {0,0,0};
        int k = 0;
        for(int i = 0; i < widthReversedRGBImage; i++) {
            for (int j = 0; j < heightReversedRGBImage; j++) {
                pixels[0] = redPixels.get(k);
                pixels[1] = greenPixels.get(k);
                pixels[2] = bluePixels.get(k);
                reversedRGBImage.getProcessor().putPixel(i, j, pixels);
                k++;
            }
        }
        reversedRGBImage.show();
    }

    private ImagePlus getOpenImageByTitle(String title) {
        IJ.selectWindow(title);
        ImagePlus image = IJ.getImage().duplicate();
        return image;
    }

    private List<Integer> getPixelsFrom8BitImage(ImagePlus image, List<Integer> pixels) {
        for(int i = 0; i < image.getProcessor().getWidth(); i++) {
            for(int j = 0; j < image.getProcessor().getHeight(); j++) {
                int pixel = image.getProcessor().getPixel(i,j);
                pixels.add(pixel);
            }
        }
        return pixels;
    }
}
