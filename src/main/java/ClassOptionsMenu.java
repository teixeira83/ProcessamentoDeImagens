import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;

import java.awt.*;
import java.awt.event.ActionEvent;


public class ClassOptionsMenu implements PlugIn, DialogListener {

    private String chosenOptionRadioButton = "";

    @Override
    public void run(String s) {
        showGraphicInterface();
    }

    public void showGraphicInterface() {


        GenericDialog grapichInterface = new GenericDialog("Menu de Plugins: ");

        /*
        * Disable the OK button that is standard of generic dialog
        * */
        Button[] btns = grapichInterface.getButtons();
        btns[0].setVisible(false);

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
        grapichInterface.addMessage("Fundamentos - Converter de RGB para escala de cinza :");

        String[] methods = {"Média dos Canais RGB", "Luminância Analógica","Luminância Digital"};
        grapichInterface.addRadioButtonGroup("Escolha um dos três métodos", methods, 3, 1,"Média dos Canais RGB");

        Button btnRGBToGreyScaleWithBookMethods = new Button("Transformar em Escala de Cinca!");
        btnRGBToGreyScaleWithBookMethods.addActionListener(e -> {
            String chosenOption = grapichInterface.getNextRadioButton();
            radioButtonPerformed(chosenOption, grapichInterface.getNextBoolean());
        });
        grapichInterface.addCheckbox("Criar uma nova imagem?", true);
        grapichInterface.add(btnRGBToGreyScaleWithBookMethods);

        grapichInterface.addMessage("Para configurar os atributos da imagem :");
        Button btnUpdatePicture = new Button("Configurar imagem...");
        btnUpdatePicture.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnUpdatePicture);

        grapichInterface.addMessage("         ------------------");
        grapichInterface.addMessage("Aula 5 :");
        Button btnCreateHistogram = new Button("Criar Histograma...");
        btnCreateHistogram.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnCreateHistogram);

        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if (grapichInterface.wasCanceled()) {
            IJ.showMessage("Menu de Plugins", "Menu encerrado com sucesso.");
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
        if(e.getActionCommand() == "Configurar imagem...") {
            IJ.run("Compile and Run...", "compile=./src/main/java/UpdatePicture.java");
        }
        if(e.getActionCommand() == "Criar Histograma...") {
            IJ.run("Compile and Run...", "compile=./src/main/java/HistogramExpansionandEqualization.java");
        }
    }

    public void radioButtonPerformed(String chosenOption, Boolean createNewImage) {
        RGBToGreyScaleWithBookMethods bookMethods = new RGBToGreyScaleWithBookMethods(chosenOption, createNewImage);
        bookMethods.run("");
    }

    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return false;
    }
}
