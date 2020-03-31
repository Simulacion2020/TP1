package sample.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        IGeneradorAleatorio generador = new GeneradorCongruencialLineal(95,31,17,653);
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
        }
    }
}
