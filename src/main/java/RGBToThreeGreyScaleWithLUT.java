import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.LUT;

public class RGBToThreeGreyScaleWithLUT implements PlugIn {

    @Override
    public void run(String s) {
        IJ.run("Compile and Run...", "compile=./src/main/java/RGBToThreeGreyScale.java");
        IJ.wait(3500);

        ImagePlus redImage = getOpenImageByTitle("Imagem Vermelha");
        ImagePlus greenImage = getOpenImageByTitle("Imagem Verde");
        ImagePlus blueImage = getOpenImageByTitle("Imagem Azul");

        byte[] red = new byte[256];
        byte[] green = new byte[256];
        byte[] blue = new byte[256];
        byte[] empty = new byte[256];

        for (int i = 0; i < 256; i++) {
            red[i] = (byte) i;
            green[i] = (byte) i;
            blue[i] = (byte) i;
            empty[i] = 0;
        }

        LUT redChannelLUT = new LUT(red, empty, empty);
        LUT greenChannelLUT = new LUT(empty, green, empty);
        LUT blueChannelLUT = new LUT(empty, empty, blue);

        redImage.getProcessor().setLut(redChannelLUT);
        greenImage.getProcessor().setLut(greenChannelLUT);
        blueImage.getProcessor().setLut(blueChannelLUT);

        redImage.show();
        greenImage.show();
        blueImage.show();
    }

    private ImagePlus getOpenImageByTitle(String title) {
        IJ.selectWindow(title);
        ImagePlus image = IJ.getImage().duplicate();
        return image;
    }
}
