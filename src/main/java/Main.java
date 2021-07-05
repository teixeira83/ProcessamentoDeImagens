import ij.IJ;
import ij.ImageJ;

public class Main {

    public static void main(String args[]) {
        new ImageJ();
        //IJ.run("Compile and Run...", "compile=./src/main/java/ClassOptionsMenu.java");
        IJ.run("Compile and Run...", "compile=./src/main/java/UpdatePicture.java");
    }
}
