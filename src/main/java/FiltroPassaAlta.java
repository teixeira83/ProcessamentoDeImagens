import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class FiltroPassaAlta implements PlugIn {
    @Override
    public void run(String s) {
//        IJ.run("AuPbSn 40");
        IJ.run("Boats");
        ImagePlus originalImage = IJ.getImage();
        ImageProcessor originalProcessor = originalImage.getProcessor();
        ImagePlus newImage = originalImage.duplicate();
        ImageProcessor newProcessor = newImage.getProcessor();
        int pixel;
        int soma = 0;
        int k = -1;
        for(int i = 1; i < newProcessor.getWidth(); i++) {
            for(int j = 1; j < newProcessor.getHeight(); j++) {
                for(int x = -1; x < 2; x++) {
                    for(int y = 1; y > -2; y--) {
                        pixel = originalProcessor.getPixel(i + x, j + y);
                        if((x == 0) && (y == 0)) {
                            soma += (pixel * 9);
                        } else {
                            soma += (pixel * k);
                        }
                    }
                }
                if(soma < 0) { soma = 0; }
                if(soma > 255) { soma = 255; }
                newProcessor.putPixel(i,j, soma);
                soma = 0;
            }
        }
        newImage.show();
    }
}
