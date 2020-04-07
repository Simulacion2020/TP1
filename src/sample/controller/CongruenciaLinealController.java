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
import sample.model.GeneradorCongruencialLineal;
import sample.model.IGeneradorAleatorio;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CongruenciaLinealController {


    @FXML
    TextField x0linealtext;
    @FXML
    TextField clinealtext;
    @FXML
    TextField klinealtext;
    //    @FXML
//    TextField mlinealtext;
    @FXML
    TextField glinealtext;
    @FXML
    TextField muestralinealtext;
    @FXML
    Label camposInvalidosLinealLabel;
    @FXML
    TableView tableLineal;
    @FXML
    TableColumn iCol;

    @FXML
    TableColumn riCol;

    @FXML
    GridPane root;


    public static final String Column1MapKey = "i";
    public static final String Column2MapKey = "ri";


    @FXML
    protected void handleSalirButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void handleGenerarLineal(ActionEvent event) {
        if (!this.validarCampos()) {
            camposInvalidosLinealLabel.setText("Los campos ingresados no son validos");
            System.out.println("Los campos ingresados no son validos");
        } else {
            camposInvalidosLinealLabel.setText("");
            System.out.println("Campos validos, realizamos calculos para generar tabla");
            this.generarTabla();
        }
    }

    private void generarTabla() {
        tableLineal = new TableView<>(generateDataInMap());
        tableLineal.setEditable(true);

        iCol = new TableColumn("i");
        iCol.setMinWidth(100);
        iCol.setCellValueFactory(new MapValueFactory<>(Column1MapKey));

        riCol = new TableColumn("ri");
        riCol.setMinWidth(100);
        riCol.setCellValueFactory(new MapValueFactory<>(Column2MapKey));

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
        riCol.setCellFactory(cellFactoryForMap);
        tableLineal.getColumns().addAll(iCol, riCol);


        root.add(tableLineal, 0, 1);

    }

    private ObservableList<Map> generateDataInMap() {
        //TODO aca hay que revisar muchachos cual es cada campo y cual no deberia ri
        int semilla = Integer.parseInt(x0linealtext.getText());//95
        int constanteMultiplicador = Integer.parseInt(klinealtext.getText());//31  a = 1+4k
        int incremento = Integer.parseInt(clinealtext.getText());//17
        int exponenteModulo = Integer.parseInt(glinealtext.getText());//653 m=2g

        IGeneradorAleatorio generador = new GeneradorCongruencialLineal(semilla, constanteMultiplicador, incremento, exponenteModulo);
        System.out.println("Generador Congruancial Lineal");
        int max = Integer.parseInt(muestralinealtext.getText()); // deberia llegar como parametro?
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>();

            String value1 = "" + generador.GenerarAleatorio(); //esta bien esto?
            System.out.println("value1:" + value1);
            String value2 = "" + generador.getSemilla(); // y esto?
            System.out.println("value2:" + value2);
            dataRow.put(Column1MapKey, value1);
            dataRow.put(Column2MapKey, value2);

            allData.add(dataRow);
        }
        return allData;
    }

    private boolean validarCampos() {
        boolean valido = true;
        try {
            if (x0linealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int x0 = Integer.parseInt(x0linealtext.getText());
                if (x0 < 0) {
                    valido = false;
                }
            }
//            if (alinealtext.getText().trim().length() == 0) {
//                valido = false;
//            } else {
//                Integer.parseInt(alinealtext.getText());
//            }
            if (klinealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int k = Integer.parseInt(klinealtext.getText());
                if (k < 0) {
                    valido = false;
                }
            }
            if (clinealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int c = Integer.parseInt(clinealtext.getText());
                if (c < 0) {
                    valido = false;
                }
            }
            if (glinealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int g = Integer.parseInt(glinealtext.getText());
                if (g < 0) {
                    valido = false;
                }
            }
            if (muestralinealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int muestra = Integer.parseInt(muestralinealtext.getText());
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
