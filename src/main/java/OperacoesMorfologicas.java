import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.awt.*;
import java.awt.event.ActionEvent;

public class OperacoesMorfologicas implements PlugIn, DialogListener {

        ImagePlus imagemOriginal;
        ImageProcessor processadorOriginal;
    @Override
    public void run(String s) {
        IJ.open("/home/z3r0/Área de Trabalho/celulasCancer.jpg");
        this.imagemOriginal = IJ.getImage();
        GenericDialog grapichInterface = new GenericDialog("Menu de Operações MM: ");

        Button btnTransformarEmBinary = new Button("Transformar img em Binary");
        btnTransformarEmBinary.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnTransformarEmBinary);
        grapichInterface.addMessage("-----------------------");

        Button btnAplicarDilatacao = new Button("Aplicar Operação Dilatação");
        btnAplicarDilatacao.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnAplicarDilatacao);
        grapichInterface.addMessage("-----------------------");

        Button btnAplicarErosao = new Button("Aplicar Operação Erosão");
        btnAplicarErosao.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnAplicarErosao);
        grapichInterface.addMessage("-----------------------");

        Button btnAplicarFechamento = new Button("Aplicar Operação Fechamento");
        btnAplicarFechamento.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnAplicarFechamento);
        grapichInterface.addMessage("-----------------------");

        Button btnAplicarAbertura = new Button("Aplicar Operação Abertura");
        btnAplicarAbertura.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnAplicarAbertura);
        grapichInterface.addMessage("-----------------------");

        Button btnAplicarBorda = new Button("Aplicar Borda(Outline)");
        btnAplicarBorda.addActionListener(e -> actionPerformed(e));
        grapichInterface.add(btnAplicarBorda);
        grapichInterface.addMessage("-----------------------");

        grapichInterface.addDialogListener(this);
        grapichInterface.showDialog();

        if (grapichInterface.wasOKed()) {
            System.out.println("ok");
        }
        if (grapichInterface.wasCanceled()) {
            IJ.showMessage("Menu de Plugins", "Menu encerrado com sucesso.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "Transformar img em Binary") {
            IJ.run("Make Binary");
        }
        if(e.getActionCommand() == "Aplicar Operação Dilatação") {
            aplicarDilatacao();
        }
        if(e.getActionCommand() == "Aplicar Operação Erosão") {
            System.out.println("Aplicar Erosão");
        }
        if(e.getActionCommand() == "Aplicar Operação Fechamento") {
            System.out.println("Aplicar Fechamento");
        }
        if(e.getActionCommand() == "Aplicar Operação Abertura") {
            System.out.println("Aplicar Abertura");
        }
        if(e.getActionCommand() == "Aplicar Borda(Outline)") {
            System.out.println("Aplicar Borda");
        }
    }

    public void aplicarDilatacao() {
        ImagePlus imagemAberta = IJ.getImage();
        ImageProcessor processadorAberto = IJ.getProcessor();
        ImagePlus imagemDilatada = imagemAberta.duplicate();
        ImageProcessor processadorDilatado = imagemDilatada.getProcessor();
        int pixel;
        for (int i = 1; i < processadorAberto.getWidth(); i++) {
            for (int j = 1; j < processadorAberto.getHeight(); j++) {
                pixel = processadorAberto.getPixel(i,j);
                if(pixel == 255) {
                    for(int x = -1; x < 2; x++) {
                        for(int y = 1; y > -2; y--) {
                            processadorDilatado.putPixel(i + x, j + y, 255);
                        }
                    }
                }
            }
        }

        imagemAberta.close();
        imagemDilatada.show();
    }

    @Override
    public boolean dialogItemChanged(GenericDialog genericDialog, AWTEvent awtEvent) {
        return true;
    }
}
