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

        String[] plugins = {"Filtro passa-baixas de média", "Outro Fltro", "Outro Fltro2"};
        grapichInterface.addRadioButtonGroup("Escolha um filtro para aplicar :", plugins, 3, 1,"Plugins estudados na P2");

        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if (grapichInterface.wasCanceled()) {
            IJ.showMessage("Menu de Plugins", "Menu encerrado com sucesso.");
        }
    }

    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return true;
    }
}
