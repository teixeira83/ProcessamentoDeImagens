import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Collections;

public class FiltroNLModa implements PlugIn {
    @Override
    public void run(String s) {
        IJ.run("Boats");
        ImagePlus originalImage = IJ.getImage();
        ImageProcessor originalProcessor = originalImage.getProcessor();
        ImagePlus newImage = originalImage.duplicate();
        ImageProcessor newProcessor = newImage.getProcessor();
        ArrayList<Integer> pixels = new ArrayList<>();
        for(int i = 1; i < newProcessor.getWidth(); i++) {
            for(int j = 1; j < newProcessor.getHeight(); j++) {
                pixels.clear();
                for(int x = -1; x < 2; x++) {
                    for(int y = 1; y > -2; y--) {
                        pixels.add(originalProcessor.getPixel(i + x, j + y));
                    }
                }
                Collections.sort(pixels);
                int contador = 1;
                int contadorMaximo = 1;
                int moda = pixels.get(0);
                int ultimaPosicaoVerificada = 0;
                for(int k = 0; k < pixels.size() - 1; k++) {
                    int valorAtual = pixels.get(k);
                    for(int l = k + 1; l < pixels.size(); l++) {
                        if(valorAtual != pixels.get(l)) { break; }
                        else {
                            contador++;
                            ultimaPosicaoVerificada = l;
                        }
                    }

                    if(contadorMaximo < contador) {
                        moda = pixels.get(k);
                        contador = 1;
                        k = ultimaPosicaoVerificada - 1;
                    }
                }
                newProcessor.putPixel(i,j, moda);
            }
        }
        newImage.show();
    }
}
