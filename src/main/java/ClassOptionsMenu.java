import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

import java.awt.*;
import java.awt.event.ActionEvent;


public class ClassOptionsMenu implements PlugIn, DialogListener {


    @Override
    public void run(String s) {
        showGraphicInterface();
    }

    public void showGraphicInterface() {


        GenericDialog grapichInterface = new GenericDialog("Menu de Plugins: ");
        grapichInterface.setSize(5000,5000);

        grapichInterface.addMessage("Plugins criados na aula de Processamento de Imagem :");

        Button btcCloseAllImages = new Button("Fechar Todas as Imagens!");
        btcCloseAllImages.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btcCloseAllImages);

        grapichInterface.addMessage("   ----------------------------");
        grapichInterface.addMessage("Aula 3 :");
        Button btnRgbToThreeGreyScale = new Button("Transformar Três Escalas de Cinca");
        btnRgbToThreeGreyScale.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnRgbToThreeGreyScale);

        Button btnGreyScaleWithLUT = new Button("Escalas de Cinca Com LUT");
        btnGreyScaleWithLUT.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnGreyScaleWithLUT);

        Button btnReverseGreyScaleToRGB = new Button("Reverter para RGB");
        btnReverseGreyScaleToRGB.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnReverseGreyScaleToRGB);

        grapichInterface.addMessage("------------------------------");
        grapichInterface.addMessage("Aula 4 :");
        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if (grapichInterface.wasCanceled()) {
            IJ.showMessage("PlugIn cancelado!");
        } else {
            if (grapichInterface.wasOKed()) {
                IJ.showMessage("Menu de Plugins ", "O menu foi encerrado corretamente");
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Fechar Todas as Imagens!") {
            System.out.println("Fechando todas as imagens abertas....");
            IJ.run("Close All");
            System.out.println("As imagens foram fechadas....");
        }
        if(e.getActionCommand() == "Transformar Três Escalas de Cinca") {
            System.out.println("Executando Plugin....");
            System.out.println("A Imagem será transformada em três novas imagens em escalas de Cinza...");
            IJ.run("Compile and Run...", "compile=./src/main/java/RGBToThreeGreyScale.java");
        }
        if(e.getActionCommand() == "Escalas de Cinca Com LUT") {
            System.out.println("Executando Plugin....");
            System.out.println("Serão criadas LUTs para as cores vermelha, verde e azul...");
            IJ.run("Compile and Run...", "compile=./src/main/java/RGBToThreeGreyScaleWithLUT.java");
        }
        if(e.getActionCommand() == "Reverter para RGB") {
            System.out.println("Executando Plugin....");
            System.out.println("Criando nova imagem RGB...");
            IJ.run("Compile and Run...", "compile=./src/main/java/ReverseThreeGreyScaleToRGB.java");
        }
    }


    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return false;
    }
}
