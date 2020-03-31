package sample.model;

public class GeneradorCongruencialMultiplicativo implements IGeneradorAleatorio
{
    private int semilla;
    final private int multiplicador;
    final private int modulo;

    public GeneradorCongruencialMultiplicativo(int aSemilla, int aMultiplicador, int aModulo)
    {
        /*
            Validar valores de parametros
            0 <= semilla < modulo
            0 < modulo
            0 < multiplicador < m
        */


        this.semilla = aSemilla;
        this.multiplicador = aMultiplicador;
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
        semilla = (multiplicador * semilla)%(modulo);
    }

}
