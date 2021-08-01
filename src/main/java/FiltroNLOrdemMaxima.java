import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Collections;

public class FiltroNLOrdemMaxima implements PlugIn {
    @Override
    public void run(String s) {
        IJ.run("Boats");
        ImagePlus originalImage = IJ.getImage();
        ImageProcessor originalProcessor = originalImage.getProcessor();
        ImagePlus newImage = originalImage.duplicate();
        ImageProcessor newProcessor = newImage.getProcessor();
        ArrayList<Integer> pixels = new ArrayList<>();
        for(int i = 1; i < newProcessor.getWidth(); i++) {
            for(int j = 1; j < newProcessor.getHeight(); j++) {
                pixels.clear();
                for(int x = -1; x < 2; x++) {
                    for(int y = 1; y > -2; y--) {
                        pixels.add(originalProcessor.getPixel(i + x, j + y));
                    }
                }
                Collections.sort(pixels);
                int mediana = pixels.get(8);
                newProcessor.putPixel(i,j, mediana);
            }
        }
        newImage.show();
    }
}
