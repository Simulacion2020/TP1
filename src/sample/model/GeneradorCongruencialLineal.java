package sample.model;

//import com.sun.org.apache.xpath.internal.operations.Mod;

public class GeneradorCongruencialLineal implements IGeneradorAleatorio {
    private int semilla;
    final private int multiplicador;
    final private int incremento;
    final private int modulo;

    public GeneradorCongruencialLineal(int aSemilla, int aMultiplicador, int aIncremento, int aModulo)
    {
        /*
            Validar valores de parametros
            0 <= semilla < modulo
            0 < modulo
            0 < multiplicador < m
            0 <= incremento < m
        */


        this.semilla = aSemilla;
        this.multiplicador = aMultiplicador;
        this.incremento = aIncremento;
        this.modulo = aModulo;
    }

    @Override
    public int getSemilla() {
        return semilla;
    }

    @Override
    /*
        Genera un nuevo numero aleatorio a partir de la semilla acual X(i)
    */
    public double GenerarAleatorio() {
        double aleatorio;
        //Generamos la nueva semilla
        SiguienteSemilla();
        //calculamos el nuevo aleatorio entre 0 y 1
        aleatorio = ((double) semilla) / ((double)(modulo - 1));

        return aleatorio;
    }

    @Override
    /*
        genera X(i+1) a partir de la semilla actual X(i)
        Para conocer el valor de la semilla usar getSemilla()
    */
    public void SiguienteSemilla()
    {
        semilla = (multiplicador * semilla + incremento)%(modulo);
    }
}
