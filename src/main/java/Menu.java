import ij.IJ;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

import java.awt.*;

public class Menu implements PlugIn, DialogListener {

    @Override
    public void run(String s) {
        showGraphicInterface();
    }
    public void showGraphicInterface() {
        GenericDialog grapichInterface = new GenericDialog("Menu: ");

        String[] plugins = {"Plugins estudados na P1", "Plugins estudados na P2"};
        grapichInterface.addRadioButtonGroup("Quais plugins você quer acessar?", plugins, 2, 1,"Plugins estudados na P2");

        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if (grapichInterface.wasOKed()) {
            String chosenOption = grapichInterface.getNextRadioButton();

            if(chosenOption == "Plugins estudados na P1"){
                IJ.run("Compile and Run...", "compile=./src/main/java/ClassOptionsMenuP1.java");
            }
            if(chosenOption == "Plugins estudados na P2"){
                IJ.run("Compile and Run...", "compile=./src/main/java/ClassOptionsMenuP2.java");
            }
        }
        if (grapichInterface.wasCanceled()) {
            IJ.showMessage("Menu", "Menu encerrado com sucesso.");
        }
    }

    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return true;
    }
}
