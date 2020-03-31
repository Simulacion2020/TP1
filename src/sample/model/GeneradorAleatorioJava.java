package sample.model;

public class GeneradorAleatorioJava implements IGeneradorAleatorio
{
    @Override
    public int getSemilla() {
        return 0;
    }

    @Override
    public double GenerarAleatorio()
    {
        return Math.random();
    }

    @Override
    public void SiguienteSemilla() {

    }


}
