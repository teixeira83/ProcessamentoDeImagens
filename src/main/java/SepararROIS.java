import ij.IJ;
import ij.ImagePlus;
import ij.gui.Roi;
import ij.plugin.PlugIn;
import ij.plugin.filter.ParticleAnalyzer;
import ij.plugin.frame.RoiManager;

public class SepararROIS implements PlugIn {


    @Override
    public void run(String s) {
        IJ.open("/home/z3r0/Área de Trabalho/sprite.jpg");

        ImagePlus imagemOriginal = IJ.getImage();

        IJ.run("Convert to Mask");
        IJ.run("Fill Holes");
        IJ.run("Analyze Particles...", "add");

        RoiManager rm = RoiManager.getRoiManager();
        String path = "./src/imgs/";

        for(int i = 0; i < rm.getCount(); i++){
                rm.select(imagemOriginal, i);
                ImagePlus imagemCrop = imagemOriginal.crop();
                IJ.save(imagemCrop, path + i +".png");
        }
    }
}
