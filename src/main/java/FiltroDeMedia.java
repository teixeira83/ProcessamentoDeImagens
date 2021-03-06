import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class FiltroDeMedia implements PlugIn {
    @Override
    public void run(String s) {
        IJ.run("Boats");
        ImagePlus originalImage = IJ.getImage();
        ImageProcessor originalProcessor = originalImage.getProcessor();
        ImagePlus newImage = originalImage.duplicate();
        ImageProcessor newProcessor = newImage.getProcessor();
        int pixel;
        float soma = 0;
        double k = 0.11;
        for(int i = 1; i < newProcessor.getWidth(); i++) {
            for(int j = 1; j < newProcessor.getHeight(); j++) {
                for(int x = -1; x < 2; x++) {
                    for(int y = 1; y > -2; y--) {
                        pixel = originalProcessor.getPixel(i + x, j + y);
                        soma += (pixel * k);
                    }
                }
                int newPixel = (int) soma;
                if (newPixel < 0) { newPixel = 0; }
                newProcessor.putPixel(i,j, newPixel);
                soma = 0;
            }
        }
        newImage.show();
    }
}
