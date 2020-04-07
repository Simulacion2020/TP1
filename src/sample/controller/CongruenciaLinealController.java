package sample.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.model.GeneradorCongruencialLineal;
import sample.model.IGeneradorAleatorio;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CongruenciaLinealController {


    @FXML
    TextField x0linealtext;
    @FXML
    TextField clinealtext;
    @FXML
    TextField klinealtext;
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
    TableColumn xiCol;
    @FXML
    TableColumn xi1Col;
    @FXML
    GridPane root;
    @FXML
    Button buttonChiLineal;
    @FXML
    Button buttonHistogramaLineal;


    public static final String Column1MapKey = "i";
    public static final String Column2MapKey = "xi";
    public static final String Column3MapKey = "xi+1";

    int DATA_SIZE = 10;
    float data[] = new float[DATA_SIZE];
    int group[] = new int[10]; //cantidad de intervalos


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
            buttonChiLineal.setDisable(false);
            buttonHistogramaLineal.setDisable(false);
        }
    }

    @FXML
    protected void handleHistogramaButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/histogramaView.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Histograma");
            Scene scene = generarHistograma();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene generarHistograma() {
        prepareData();
        groupData();

        Label labelInfo = new Label();
        labelInfo.setText("Histograma Congruencia lineal");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart =
                new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Rango");
        yAxis.setLabel("Cantidad de numeros aleatorios");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Frecuencia");
        series1.getData().add(new XYChart.Data("0-0.1", group[0]));
        series1.getData().add(new XYChart.Data("0.1-0.2", group[1]));
        series1.getData().add(new XYChart.Data("0.2-0.3", group[2]));
        series1.getData().add(new XYChart.Data("0.3-0.4", group[3]));
        series1.getData().add(new XYChart.Data("0.4-0.5", group[4]));

        series1.getData().add(new XYChart.Data("0.5-0.6", group[5]));
        series1.getData().add(new XYChart.Data("0.6-0.7", group[6]));
        series1.getData().add(new XYChart.Data("0.7-0.8", group[7]));
        series1.getData().add(new XYChart.Data("0.8-0.9", group[8]));
        series1.getData().add(new XYChart.Data("0.9-1.0", group[9]));

        barChart.getData().addAll(series1);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, barChart);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 800, 450);

        return scene;
    }

    //generate dummy random data
    private void prepareData() {

        Random random = new Random();
        for (int i = 0; i < DATA_SIZE; i++) {
            data[i] = random.nextFloat();
            System.out.println(data[i]);
        }
    }

    //count data population in groups
    private void groupData() {
        for (int i = 0; i < 10; i++) {
            group[i] = 0;
        }
        for (int i = 0; i < DATA_SIZE; i++) {
            if (data[i] <= 0.1) {
                group[0]++;
            } else if (data[i] <= 0.2) {
                group[1]++;
            } else if (data[i] <= 0.3) {
                group[2]++;
            } else if (data[i] <= 0.4) {
                group[3]++;
            } else if (data[i] <= 0.5) {
                group[4]++;
            } else if (data[i] <= 0.6) {
                group[5]++;
            } else if (data[i] <= 0.7) {
                group[6]++;
            } else if (data[i] <= 0.8) {
                group[7]++;
            } else if (data[i] <= 0.9) {
                group[8]++;
            } else if (data[i] <= 1) {
                group[9]++;
            }
        }
    }


    @FXML
    protected void handleChi2ButtonAction(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL urlLocation = getClass().getResource("/sample/view/chicuadradoView.fxml");
        fxmlLoader.setLocation(urlLocation);
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Prueba Chi²");
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarTabla() {
        tableLineal = new TableView<>(generateDataInMap());
        tableLineal.setEditable(true);

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
        tableLineal.getColumns().addAll(iCol, xiCol, xi1Col);


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
        int max = Integer.parseInt(muestralinealtext.getText());
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 0; i < max; i++) {
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
