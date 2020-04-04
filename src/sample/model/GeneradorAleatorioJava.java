package sample.model;

public class GeneradorAleatorioJava implements IGeneradorAleatorio
{
    public int getSemilla() {
        return 0;
    }

    public double GenerarAleatorio()
    {
        return Math.random();
    }

    public void SiguienteSemilla() {

    }


}
