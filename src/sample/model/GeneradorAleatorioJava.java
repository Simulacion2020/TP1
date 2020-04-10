package sample.model;

public class GeneradorAleatorioJava implements IGeneradorAleatorio
{
    public long getSemilla() {
        return 0;
    }

    public float GenerarAleatorio()
    {
        return (float) Math.random();
    }

    public void SiguienteSemilla() {

    }


}
