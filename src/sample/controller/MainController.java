package sample.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.model.GeneradorAleatorioJava;
import sample.model.GeneradorCongruencialLineal;
import sample.model.GeneradorCongruencialMultiplicativo;
import sample.model.IGeneradorAleatorio;

import java.io.IOException;
import java.net.URL;

public class MainController {
    private int tamañoMuestra;
    private int semillaIngresada;
    private int exponenteModuloIngresado;
    private int constanteMultiplicadorIngresado;
    private int incrementoIngresado;
    private IGeneradorAleatorio generador;
    private double[] listaAleatorios;
    private long[] listaSemillas;

    // visual
    @FXML
    CheckBox CheckBoxLineal;
    @FXML
    CheckBox CheckBoxMultiplicativa;
    @FXML
    CheckBox CheckBoxLenguaje;
    @FXML
    Label LabelTitle;

    @FXML
    TextField x0linealtext;
    @FXML
    TextField clinealtext;
    @FXML
    TextField klinealtext;
    @FXML
    TextField glinealtext;

    @FXML
    RadioButton radioLineal;
    @FXML
    RadioButton radioMultiplicativo;
    @FXML
    RadioButton radioLenguajeJava;

    @FXML
    ToggleGroup Metodos;

    public MainController() {

    }

    public void opcionGenerarSecuenciaDeAleatoriosJava(int aTamañoMuestra) {
        generador = new GeneradorAleatorioJava();
        tamañoMuestra = aTamañoMuestra;

        generarAleatorios();
    }

    public void opcionGenerarSecuenciaDeAleatoriosCongruencialLineal
            (int aTamañoMuestra, int aSemilla, int aConstanteMultiplicador, int aIncremento, int aExponenteModulo) {
        this.tamañoMuestra = aTamañoMuestra;
        this.semillaIngresada = aSemilla;
        this.constanteMultiplicadorIngresado = aConstanteMultiplicador;
        this.incrementoIngresado = aIncremento;
        this.exponenteModuloIngresado = aExponenteModulo;

        generador = new GeneradorCongruencialLineal(semillaIngresada, constanteMultiplicadorIngresado, incrementoIngresado, exponenteModuloIngresado);

        generarAleatorios();
    }

    public void opcionGenerarSecuenciaDeAleatoriosCongruencialMultiplicativo
            (int aTamañoMuestra, int aSemilla, int aConstanteMultiplicador, int aExponenteModulo) {
        this.tamañoMuestra = aTamañoMuestra;
        this.semillaIngresada = aSemilla;
        this.constanteMultiplicadorIngresado = aConstanteMultiplicador;
        this.exponenteModuloIngresado = aExponenteModulo;

        generador = new GeneradorCongruencialMultiplicativo(semillaIngresada, constanteMultiplicadorIngresado, exponenteModuloIngresado);

        generarAleatorios();
    }

    public void generarAleatorios() {

        if (generador != null) {
            listaAleatorios = new double[tamañoMuestra];
            listaSemillas = new long[tamañoMuestra];
            for (int i = 0; i < tamañoMuestra; i++) {
                listaAleatorios[i] = generador.GenerarAleatorio();
                listaSemillas[i] = generador.getSemilla();
            }
        } else {

        }
        mostrar();
    }

    public void mostrar() {
        for (int i = 0; i < tamañoMuestra; i++) {
            System.out.println(listaAleatorios[i] + " " + listaSemillas[i]);
        }

    }

    @FXML
    protected void handleGenerarLinealButtonAction(ActionEvent event) {

        String title = "";
        String location = "";
        String color = "#0076a3";
        int left = 10;
        int top = 10;
        int size = 30;

        location = "/sample/view/congruenciaLinealView.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource(location);
        fxmlLoader.setLocation(urlLocation);

        if (radioLineal.isSelected()) {
            title = "Generador Congruencial Lineal";
    //        location = "/sample/view/congruenciaLinealView.fxml";
        } else if (radioLenguajeJava.isSelected()) {
            title = "Generador Lenguaje Java";
    //        location = "/sample/view/LenguajeJavaView.fxml";

    /*
    Deshabilita los campos que no necesita.. pero dan Excepcion
            x0linealtext.setText("1");
            x0linealtext.setVisible(false);
            clinealtext.setText("1");
            clinealtext.setVisible(false);
            glinealtext.setText("1");
            glinealtext.setVisible(false);
            klinealtext.setText("1");
            klinealtext.setVisible(false);*/
        } else if (radioMultiplicativo.isSelected()) {
            title = "Generador Congruencial Multiplicativa";
      //      location = "/sample/view/congruenciaMultiplicativaView.fxml";

            /*
    Deshabilita los campos que no necesita.. pero dan Excepcion
            clinealtext.setText("1");
            clinealtext.setVisible(false);*/
        } else {
            title = "Por favor seleccione un metodo";
            location = "/sample/view/main.fxml";
            left = 120;
            top = 0;
            color = "#ff0000";
            size = 15;
        }

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            LabelTitle = new Label();
            LabelTitle.setPadding(new Insets(top, 10, 10, left));
            LabelTitle.setAlignment(Pos.CENTER);
            LabelTitle.setTextFill(Color.web(color));
            LabelTitle.setFont(new Font("Arial", size));
            LabelTitle.setText(title);
            anchorPane.requestFocus();
            anchorPane.getChildren().addAll(LabelTitle);
            stage.setTitle(title);
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
