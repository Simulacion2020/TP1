package sample.model;

import java.text.DecimalFormat;

public class GeneradorAleatorioJava implements IGeneradorAleatorio {
    public int getSemilla() {
        return 0;
    }

    public String GenerarAleatorio() {
        float aleatorio = (float) Math.random();
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        String aleatorioString = decimalFormat.format(aleatorio);
        return aleatorioString;
    }

    public void SiguienteSemilla() {

    }


}
