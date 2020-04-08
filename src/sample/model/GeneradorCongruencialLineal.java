package sample.model;

//import com.sun.org.apache.xpath.internal.operations.Mod;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GeneradorCongruencialLineal implements IGeneradorAleatorio {
    private int semilla;
    final private int multiplicador;
    final private int incremento;
    final private int modulo;

    public GeneradorCongruencialLineal(int aSemilla, int aConstanteMultiplicador, int aIncremento, int aExponenteModulo) {
        /*
            Validar valores de parametros
            0 <= semilla < modulo
            0 < modulo
            0 < multiplicador < m
            0 <= incremento < m
        */
        this.semilla = aSemilla;
        this.multiplicador = (1 + aConstanteMultiplicador * 4);
        this.incremento = aIncremento;
        this.modulo = (int) Math.pow(2, aExponenteModulo);
    }

    public int getSemilla() {
        return semilla;
    }

    /*
        Genera un nuevo numero aleatorio a partir de la semilla actual X(i)
    */
    public float GenerarAleatorio() {
        float aleatorio;
        //Generamos la nueva semilla
        SiguienteSemilla();
        //calculamos el nuevo aleatorio entre 0 y 1
        aleatorio = ((float) semilla) / ((float) (modulo - 1));
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        decimalFormat.format(aleatorio);
        return aleatorio;
    }

    /*
        genera X(i+1) a partir de la semilla actual X(i)
        Para conocer el valor de la semilla usar getSemilla()
    */
    public void SiguienteSemilla() {
        semilla = (multiplicador * semilla + incremento) % (modulo);
    }


}
