package sample.model;

import java.util.ArrayList;

public interface IGeneradorAleatorio {
    public long getSemilla();

    public float GenerarAleatorio();

    public void SiguienteSemilla();
}
