package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class InicioController {

    @FXML
    private Button btnInicio;

    @FXML
    private javafx.scene.control.Button closeButton;


    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/generadoresView.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Trabajo Practico 1");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            //stage.initOwner(anchorPane.getScene().getWindow());
            //stage.setMaximized(true);
            closeCurrent(event);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void closeCurrent(ActionEvent e) {
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
