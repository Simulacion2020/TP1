package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.model.GeneradorAleatorioJava;
import sample.model.IGeneradorAleatorio;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class AleatorioJavaController {


    @FXML
    TextField muestraAleatorioJavaText;
    @FXML
    Label camposInvalidosAleatorioJavaLabel;
    @FXML
    TableView tableAleatorioJava;
    @FXML
    TableColumn iCol;
    @FXML
    TableColumn ajCol;

    @FXML
    GridPane root;


    public static final String Column1MapKey = "i";
    public static final String Column2MapKey = "Aleatorio";


    @FXML
    protected void handleSalirButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void handleGenerarAleatorioJava(ActionEvent event) {
        if (!this.validarCampos()) {
            camposInvalidosAleatorioJavaLabel.setText("Los campos ingresados no son validos");
            System.out.println("Los campos ingresados no son validos");
        } else {
            camposInvalidosAleatorioJavaLabel.setText("");
            System.out.println("Campos validos, realizamos calculos para generar tabla");
            this.generarTabla();
        }
    }

    private void generarTabla() {
        tableAleatorioJava = new TableView<>(generateDataInMap());
        tableAleatorioJava.setEditable(true);

        iCol = new TableColumn("i");
        iCol.setMinWidth(100);
        iCol.setCellValueFactory(new MapValueFactory<>(Column1MapKey));

        ajCol = new TableColumn("xi");
        ajCol.setMinWidth(100);
        ajCol.setCellValueFactory(new MapValueFactory<>(Column2MapKey));

        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = new Callback<TableColumn<Map, String>,
                TableCell<Map, String>>() {
            @Override
            public TableCell call(TableColumn p) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }
                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
            }
        };
        iCol.setCellFactory(cellFactoryForMap);
        ajCol.setCellFactory(cellFactoryForMap);
        tableAleatorioJava.getColumns().addAll(iCol, ajCol);


        root.add(tableAleatorioJava, 0, 1);

    }

    private ObservableList<Map> generateDataInMap() {
        //TODO aca hay que revisar muchachos cual es cada campo y cual no deberia ri
        IGeneradorAleatorio generador = new GeneradorAleatorioJava();
        System.out.println("Generador Aleatorio de Java");
        int max = Integer.parseInt(muestraAleatorioJavaText.getText());
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i <= max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            DecimalFormat decimalFormat = new DecimalFormat("#.####");
            String value1 = decimalFormat.format(generador.GenerarAleatorio());
            dataRow.put(Column1MapKey, "" + i);
            dataRow.put(Column2MapKey, value1);

            allData.add(dataRow);
        }
        return allData;
    }

    private boolean validarCampos() {
        boolean valido = true;
        try {
            if (muestraAleatorioJavaText.getText().trim().length() == 0) {
                valido = false;
            } else {
                int muestra = Integer.parseInt(muestraAleatorioJavaText.getText());
                if (muestra < 0) {
                    valido = false;
                }
            }
        } catch (Exception e) {
            valido = false;
        }
        return valido;
    }


    @FXML
    protected void handleVolverButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/main.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Menu principal");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
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
