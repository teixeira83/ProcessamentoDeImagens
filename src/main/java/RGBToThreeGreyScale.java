import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class RGBToThreeGreyScale implements PlugIn {

    public void run(String arg) {
        IJ.run("Clown");
        ImagePlus image = IJ.getImage();
        ImageProcessor processor = image.getProcessor();

        ImagePlus redImage = createImage("Imagem Vermelha", processor);
        ImagePlus greenImage = createImage("Imagem Verde", processor);
        ImagePlus blueImage = createImage("Imagem Azul", processor);

        int pixel[] = {0,0,0};

        System.out.println("Processando as Imagens...");
        for(int i = 0; i < processor.getWidth(); i++) {
            for(int j = 0; j < processor.getHeight(); j++) {
                pixel = processor.getPixel(i, j, pixel);

                redImage.getProcessor().putPixel(i, j, pixel[0]);
                greenImage.getProcessor().putPixel(i, j, pixel[1]);
                blueImage.getProcessor().putPixel(i, j, pixel[2]);
            }
        }

        redImage.show();
        greenImage.show();
        blueImage.show();
    }

    private ImagePlus createImage(String title, ImageProcessor processor) {
        return IJ.createImage(title, "8-bit", processor.getWidth(), processor.getHeight(), 1);
    }
}
