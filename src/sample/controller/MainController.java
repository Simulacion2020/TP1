package sample.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.GeneradorAleatorioJava;
import sample.model.GeneradorCongruencialLineal;
import sample.model.IGeneradorAleatorio;

import java.io.IOException;
import java.net.URL;

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

    @FXML
    protected void handleGenerarLinealButtonAction(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/congruenciaLinealView.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Generador Congruencia Lineal");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            closeCurrent(event);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleGenerarMultiplicativaButtonAction(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/congruenciaMultiplicativaView.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Generador Congruencia Multiplicativa");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            closeCurrent(event);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleFrecuenciaLenguajeButtonAction(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/frecuenciaLenguajeView.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Prueba Frecuencia Lenguaje");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            closeCurrent(event);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleFrecuenciaLinealButtonAction(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/frecuenciaLinealView.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Prueba Frecuencia Multiplicativa");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            closeCurrent(event);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void handleSalirButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    private void closeCurrent(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
