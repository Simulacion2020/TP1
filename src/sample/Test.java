package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controller.MainController;
import sample.model.*;
import java.text.*;
public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        MainController controlador = new MainController();

        float[] listaValores = {0.15f, 0.22f, 0.41f, 0.65f, 0.84f, 0.81f, 0.62f, 0.45f, 0.32f, 0.07f, 0.11f, 0.29f, 0.58f, 0.73f,
                0.93f, 0.97f, 0.79f, 0.55f, 0.35f, 0.09f, 0.99f, 0.51f, 0.35f, 0.02f, 0.19f, 0.24f, 0.98f, 0.10f, 0.31f, 0.17f};

        float[][] intervalos = Estadistica.definirIntervalos(0,1, 5);

        int[] frecuencias = Estadistica.definirTablaDeFrecuencias(intervalos, listaValores);
        float[] frecuenciasEsperadas = {6,6,6,6,6};
        float[] desviaciones = Estadistica.calcularDesviaciones(frecuenciasEsperadas, frecuencias);

        for (int i = 0; i < intervalos.length; i++) {
            System.out.println("limiteInferior: " + intervalos[i][0] + " - limiteSuperior: " + intervalos[i][1] + " - frecuencia: " + frecuencias[i] + " - EstadisticoPrueba: " + desviaciones[i]);
        }

        System.out.println("EstaditicoPrueba: " + Estadistica.calcularEstadisticoPrueba(desviaciones));
/*
        controlador.opcionGenerarSecuenciaDeAleatoriosCongruencialLineal(20.95,31,17,653);
        controlador.opcionGenerarSecuenciaDeAleatoriosCongruencialMultiplicativo(20.95,31,653);
        controlador.opcionGenerarSecuenciaDeAleatoriosJava(20);
*/

        /*IGeneradorAleatorio generador = new GeneradorCongruencialLineal(95,31,17,653);
        System.out.println("Generador Congruancial Lineal");
        for (int i = 0; i < 10; i++) {
            System.out.println("    aleatorio: " +  generador.GenerarAleatorio() + " - semilla: "+ generador.getSemilla());
        }
        System.out.println("Generador Congruancial multiplicativo");
        generador = new GeneradorCongruencialMultiplicativo(95,31,653);
        for (int i = 0; i < 10; i++) {
            System.out.println("    aleatorio: " +  generador.GenerarAleatorio() + " - semilla: "+ generador.getSemilla());
        }

        System.out.println("Generador Congruancial Java");
        generador = new GeneradorAleatorioJava();
        for (int i = 0; i < 10; i++) {
            System.out.println("    aleatorio: " +  generador.GenerarAleatorio() + " - semilla: "+ generador.getSemilla());
        }*/
    }
}
