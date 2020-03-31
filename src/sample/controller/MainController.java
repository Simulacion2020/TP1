package sample.controller;

import sample.model.GeneradorAleatorioJava;
import sample.model.GeneradorCongruencialLineal;
import sample.model.IGeneradorAleatorio;

public class MainController
{
    private int tamañoMuestra;
    private int semillaIngresada;
    private int moduloIngresado;
    private int multiplicadorIngresado;
    private int incrementoIngresado;
    private IGeneradorAleatorio generador;
    private double[] listaAleatorios;
    private int[] listaSemillas;

    public MainController()
    {

    }

    public void opcionGenerarSecuenciaDeAleatoriosJava(int aTamañoMuestra)
    {
        generador = new GeneradorAleatorioJava();
        tamañoMuestra = aTamañoMuestra;

        generarAleatorios();
    }

    public void opcionGenerarSecuenciaDeAleatoriosCongruencialLineal
            (int aTamañoMuestra, int aSemilla, int aMultiplicador, int aIncremento, int aModulo)
    {
        this.tamañoMuestra = aTamañoMuestra;
        this.semillaIngresada = aSemilla;
        this.multiplicadorIngresado = aMultiplicador;
        this.incrementoIngresado = aIncremento;
        this.moduloIngresado = aModulo;

        generador = new GeneradorCongruencialLineal(semillaIngresada, multiplicadorIngresado, incrementoIngresado, moduloIngresado);

        generarAleatorios();
    }

    public void opcionGenerarSecuenciaDeAleatoriosCongruencialMultiplicativo
            (int aTamañoMuestra, int aSemilla, int aMultiplicador, int aModulo)
    {
        this.tamañoMuestra = aTamañoMuestra;
        this.semillaIngresada = aSemilla;
        this.multiplicadorIngresado = aMultiplicador;
        this.moduloIngresado = aModulo;

        generador = new GeneradorCongruencialLineal(semillaIngresada, multiplicadorIngresado, incrementoIngresado, moduloIngresado);

        generarAleatorios();
    }

    public void generarAleatorios()
    {

        if (generador != null )
        {
            listaAleatorios = new double[tamañoMuestra];
            listaSemillas = new int[tamañoMuestra];
            for (int i = 0; i < tamañoMuestra; i++)
            {
                listaAleatorios[i] = generador.GenerarAleatorio();
                listaSemillas[i]   = generador.getSemilla();
            }
        } else
        {

        }
        mostrar();
    }

    public void mostrar()
    {
        for (int i = 0; i < tamañoMuestra; i++) {
            System.out.println(listaAleatorios[i] + " " +listaSemillas[i]);
        }

    }
}
