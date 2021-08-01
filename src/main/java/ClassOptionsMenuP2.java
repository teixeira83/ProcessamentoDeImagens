import ij.IJ;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

import java.awt.*;

public class ClassOptionsMenuP2 implements PlugIn, DialogListener {

    private String chosenOptionRadioButton = "";

    @Override
    public void run(String s) {
        showGraphicInterface();
    }

    public void showGraphicInterface() {


        GenericDialog grapichInterface = new GenericDialog("Menu de Plugins: ");

        String[] plugins = {"Filtro passa-baixas de média", "Filtro passa-altas", "Filtro de Borda Norte"};
        grapichInterface.addRadioButtonGroup("Escolha um filtro para aplicar :", plugins, 3, 1,"Filtro passa-baixas de média");

        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if (grapichInterface.wasOKed()) {
            String chosenOption = grapichInterface.getNextRadioButton();

            if(chosenOption == "Filtro passa-baixas de média"){
                IJ.run("Compile and Run...", "compile=./src/main/java/FiltroDeMedia.java");
            }
            if(chosenOption == "Filtro passa-altas") {
                IJ.run("Compile and Run...", "compile=./src/main/java/FiltroPassaAlta.java");
            }
            if(chosenOption == "Filtro de Borda Norte") {
                IJ.run("Compile and Run...", "compile=./src/main/java/FiltroDeBorda.java");
            }
        }

        if (grapichInterface.wasCanceled()) {
            IJ.showMessage("Menu de Plugins", "Menu encerrado com sucesso.");
        }
    }

    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return true;
    }
}
