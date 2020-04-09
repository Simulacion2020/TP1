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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.model.GeneradorCongruencialLineal;
import sample.model.GeneradorCongruencialMultiplicativo;
import sample.model.IGeneradorAleatorio;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CongruenciaMultiplicativaController {
    @FXML
    TextField x0MultiplicativoText;
    @FXML
    TextField kMultiplicativoText;
    @FXML
    TextField gMultiplicativoText;
    @FXML
    TextField muestraMultiplicativoText;
    @FXML
    Label camposInvalidosMultiplicativoLabel;
    @FXML
    TableView tableMultiplicativo;
    @FXML
    TableColumn iCol;
    @FXML
    TableColumn xiCol;
    @FXML
    TableColumn xi1Col;
    @FXML
    GridPane root;

    @FXML
    TextField estadisticoMultiplicativoText;
    ////
    //Histograma
    float data[];
    int group[];
    float[][] intervalos;
    int[] frecuencias;
    int[] frecuenciasEsperadas;
    float[] desviaciones;

    float[] aListaValoresgenerador;
    float estadisticoPrueba;

    public static final String Column1MapKey = "i";
    public static final String Column2MapKey = "xi";
    public static final String Column3MapKey = "x1+i";


    @FXML
    protected void handleSalirButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void handleGenerarMultiplicativo(ActionEvent event) {
        if (!this.validarCampos()) {
            camposInvalidosMultiplicativoLabel.setText("Los campos ingresados no son validos");
            System.out.println("Los campos ingresados no son validos");
        } else {
            camposInvalidosMultiplicativoLabel.setText("");
            System.out.println("Campos validos, realizamos calculos para generar tabla");
            this.generarTabla();
        }
    }

    private void generarTabla() {
        tableMultiplicativo = new TableView<>(generateDataInMap());
        tableMultiplicativo.setEditable(true);

        iCol = new TableColumn("i");
        iCol.setMinWidth(100);
        iCol.setCellValueFactory(new MapValueFactory<>(Column1MapKey));

        xiCol = new TableColumn("xi");
        xiCol.setMinWidth(100);
        xiCol.setCellValueFactory(new MapValueFactory<>(Column2MapKey));

        xi1Col = new TableColumn("xi+1");
        xi1Col.setMinWidth(100);
        xi1Col.setCellValueFactory(new MapValueFactory<>(Column3MapKey));

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
        xiCol.setCellFactory(cellFactoryForMap);
        xi1Col.setCellFactory(cellFactoryForMap);
        tableMultiplicativo.getColumns().addAll(iCol, xiCol, xi1Col);


        root.add(tableMultiplicativo, 0, 1);

    }

    private ObservableList<Map> generateDataInMap() {
        //TODO aca hay que revisar muchachos cual es cada campo y cual no deberia ri
        int semilla = Integer.parseInt(x0MultiplicativoText.getText());//95
        int constanteMultiplicador = Integer.parseInt(kMultiplicativoText.getText());//31  a = 1+4k
        int exponenteModulo = Integer.parseInt(gMultiplicativoText.getText());//653 m=2g

        IGeneradorAleatorio generador = new GeneradorCongruencialMultiplicativo(semilla, constanteMultiplicador, exponenteModulo);
        System.out.println("Generador Congruancial Multiplicativo");
        int max = Integer.parseInt(muestraMultiplicativoText.getText());
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i <= max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            DecimalFormat decimalFormat = new DecimalFormat("#.####");
            String value1 = decimalFormat.format(generador.GenerarAleatorio());
            String value2 = "" + generador.getSemilla();
            dataRow.put(Column1MapKey, "" + i);
            dataRow.put(Column2MapKey, value2);
            dataRow.put(Column3MapKey, value1);

            allData.add(dataRow);
        }
        return allData;
    }

    private boolean validarCampos() {
        boolean valido = true;
        try {
            if (x0MultiplicativoText.getText().trim().length() == 0) {
                valido = false;
            } else {
                int x0 = Integer.parseInt(x0MultiplicativoText.getText());
                if (x0 < 0) {
                    valido = false;
                }
            }
            if (kMultiplicativoText.getText().trim().length() == 0) {
                valido = false;
            } else {
                int k = Integer.parseInt(kMultiplicativoText.getText());
                if (k < 0) {
                    valido = false;
                }
            }
            if (gMultiplicativoText.getText().trim().length() == 0) {
                valido = false;
            } else {
                int g = Integer.parseInt(gMultiplicativoText.getText());
                if (g < 0) {
                    valido = false;
                }
            }
            if (muestraMultiplicativoText.getText().trim().length() == 0) {
                valido = false;
            } else {
                int muestra = Integer.parseInt(muestraMultiplicativoText.getText());
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
