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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sample.model.*;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GeneradoresController {

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
    Label x0Label;
    @FXML
    Label cLabel;
    @FXML
    Label kLabel;
    @FXML
    Label gLabel;
    @FXML
    Label muestraLabel;
    @FXML
    Label camposInvalidosLinealLabel;
    @FXML
    Button buttonChiLineal;
    @FXML
    Button buttonHistogramaLineal;
    @FXML
    Label labelIntervalosLineal;
    @FXML
    ChoiceBox selectIntervalosLineal;

    //Tabla generador lineal
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
    public static final String Column1MapKey = "i";
    public static final String Column2MapKey = "xi";
    public static final String Column3MapKey = "xi+1";
    //
    IGeneradorAleatorio generador;
    DecimalFormat decimalFormat = new DecimalFormat("#.####");
    //Tabla chicuadrado
    @FXML
    TableView tableLinealChi;
    @FXML
    TableColumn intervaloColChi;
    @FXML
    TableColumn foColChi;
    @FXML
    TableColumn feColChi;
    @FXML
    TableColumn cColChi;
    @FXML
    TableColumn cacColChi;

    public static final String ColumnIntervaloMapKeyChi = "intervalo";
    public static final String ColumnFOMapKeyChi = "fo";
    public static final String ColumnFEMapKeyChi = "fe";
    public static final String ColumnCMapKeyChi = "C";
    public static final String ColumnCACMapKeyChi = "C(AC)";

    @FXML
    TextField estadisticoiLnealtext;
    ////
    //Histograma
    float data[];
    int group[];
    float[][] intervalos;
    int[] frecuencias;
    float[] frecuenciasEsperadas;
    float[] desviaciones;
    // float[] desviacionesAcumuladas;

    float[] aListaValoresgenerador;
    float estadisticoPrueba;

    // Radios para metodos
    @FXML
    RadioButton radioLineal;
    @FXML
    RadioButton radioMulti;
    @FXML
    RadioButton radioLenguaje;


    @FXML
    protected void handleSalirButtonAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void habilitarMetodo(ActionEvent event) {
        if (radioMulti.isSelected()) {
            //Deshabilitamos
            clinealtext.setText("1");
            clinealtext.setVisible(false);
            cLabel.setVisible(false);
            if(tableLineal != null)
            tableLineal.setVisible(false);
            //Habilitamos
            x0linealtext.setText("");
            x0linealtext.setVisible(true);
            x0Label.setVisible(true);
            glinealtext.setText("");
            glinealtext.setVisible(true);
            gLabel.setVisible(true);
            klinealtext.setText("");
            klinealtext.setVisible(true);
            kLabel.setVisible(true);
            muestralinealtext.setText("");
        }
        if (radioLenguaje.isSelected()) {
            //Deshabilitamos
            x0linealtext.setText("1");
            x0linealtext.setVisible(false);
            x0Label.setVisible(false);
            clinealtext.setText("1");
            clinealtext.setVisible(false);
            cLabel.setVisible(false);
            glinealtext.setText("1");
            glinealtext.setVisible(false);
            gLabel.setVisible(false);
            klinealtext.setText("1");
            klinealtext.setVisible(false);
            kLabel.setVisible(false);
            muestralinealtext.setText("");
            if(tableLineal != null)
                tableLineal.setVisible(false);
        }
        if (radioLineal.isSelected()) {
            //Habilitamos
            x0linealtext.setText("");
            x0linealtext.setVisible(true);
            x0Label.setVisible(true);
            clinealtext.setText("");
            clinealtext.setVisible(true);
            cLabel.setVisible(true);
            glinealtext.setText("");
            glinealtext.setVisible(true);
            gLabel.setVisible(true);
            klinealtext.setText("");
            klinealtext.setVisible(true);
            kLabel.setVisible(true);
            muestralinealtext.setText("");
            if(tableLineal != null)
                tableLineal.setVisible(false);
        }
    }

    @FXML
    protected void handleGenerarLineal(ActionEvent event) {
        if (!this.validarCampos()) {
            camposInvalidosLinealLabel.setText("Los campos ingresados no son validos");
            System.out.println("Los campos ingresados no son validos");
        } else {
            camposInvalidosLinealLabel.setText("");
            System.out.println("Campos validos, realizamos calculos para generar tabla");
            this.generarTablaGenerador();
            buttonChiLineal.setDisable(false);
            buttonHistogramaLineal.setDisable(false);
            labelIntervalosLineal.setDisable(false);
            selectIntervalosLineal.setDisable(false);
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

        for (int i = 0; i < intervalos.length; i++) {
            series1.getData().add(new XYChart.Data(intervalos[i][0] + "-" + intervalos[i][1], frecuencias[i]));
        }

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

        int intervaloSelect = Integer.parseInt((String) selectIntervalosLineal.getValue());
        if (aListaValoresgenerador.length == 0) {
            System.out.println("la lista esta vacia no se puede continuar");
        } else {

            data = new float[aListaValoresgenerador.length];
            group = new int[intervaloSelect]; //cantidad de intervalos
            for (int i = 0; i < aListaValoresgenerador.length; i++) {
                data[i] = aListaValoresgenerador[i];
            }
        }

    }

    //count data population in groups
    private void groupData() {
        for (int i = 0; i < 10; i++) {
            group[i] = 0;
        }
        for (int i = 0; i < aListaValoresgenerador.length; i++) {
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
            float estadistico = generarPruebaFrecuencia();
            boolean pasaPrueba = pasaPruebaChi2(estadistico);
            Scene scene = generarTablaChi(estadistico, pasaPrueba);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene generarTablaChi(float estadistico, boolean pasaPrueba) {
        tableLinealChi = new TableView<>(generateDataInMapChi());
        tableLinealChi.setEditable(true);

        intervaloColChi = new TableColumn("intervalo");
        intervaloColChi.setMinWidth(100);
        intervaloColChi.setCellValueFactory(new MapValueFactory<>(ColumnIntervaloMapKeyChi));

        foColChi = new TableColumn("fo");
        foColChi.setMinWidth(100);
        foColChi.setCellValueFactory(new MapValueFactory<>(ColumnFOMapKeyChi));

        feColChi = new TableColumn("fe");
        feColChi.setMinWidth(100);
        feColChi.setCellValueFactory(new MapValueFactory<>(ColumnFEMapKeyChi));

        cColChi = new TableColumn("C");
        cColChi.setMinWidth(100);
        cColChi.setCellValueFactory(new MapValueFactory<>(ColumnCMapKeyChi));


        cacColChi = new TableColumn("C(AC)");
        cacColChi.setMinWidth(100);
        cacColChi.setCellValueFactory(new MapValueFactory<>(ColumnCACMapKeyChi));

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
        intervaloColChi.setCellFactory(cellFactoryForMap);
        foColChi.setCellFactory(cellFactoryForMap);
        feColChi.setCellFactory(cellFactoryForMap);
        cColChi.setCellFactory(cellFactoryForMap);
        cacColChi.setCellFactory(cellFactoryForMap);
        tableLinealChi.getColumns().addAll(intervaloColChi, foColChi, feColChi, cColChi, cacColChi);
        GridPane rootChi = new GridPane();
        rootChi.add(tableLinealChi, 0, 0);
        estadisticoiLnealtext = new TextField();
        String resultadoPrueba;
        if (pasaPrueba)
            resultadoPrueba = "No puede rechazarse la hipótesis";
        else
            resultadoPrueba = "Se rechaza la hipótesis";

        estadisticoiLnealtext.setText("Valor Calculado: " + estadistico + " - " + resultadoPrueba);
        rootChi.add(estadisticoiLnealtext, 0, 1);


        Scene scene = new Scene(rootChi, 550, 300);
        return scene;

    }

    private ObservableList<Map> generateDataInMapChi() {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        float acumulado = 0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        for (int i = 0; i < Integer.parseInt((String) selectIntervalosLineal.getValue()); i++) {
            Map<String, String> dataRow = new HashMap<>();


            dataRow.put(ColumnIntervaloMapKeyChi, decimalFormat.format(intervalos[i][0]) + " / " + decimalFormat.format(intervalos[i][1]));
            dataRow.put(ColumnFOMapKeyChi, "" + frecuencias[i]);
            dataRow.put(ColumnFEMapKeyChi, "" + frecuenciasEsperadas[i]);
            dataRow.put(ColumnCMapKeyChi, "" + desviaciones[i]);
            acumulado = Estadistica.definirPrecision(acumulado + desviaciones[i]);
            dataRow.put(ColumnCACMapKeyChi, "" + acumulado);

            allData.add(dataRow);
        }
        return allData;
    }

    private boolean pasaPruebaChi2(float aEstadisticoPrueba)
    {
        int intervaloSelect = Integer.parseInt((String) selectIntervalosLineal.getValue());

        return Estadistica.PasaPrueba(intervaloSelect, aEstadisticoPrueba);
    }

    private float generarPruebaFrecuencia() {
        int intervaloSelect = Integer.parseInt((String) selectIntervalosLineal.getValue());

        intervalos = Estadistica.definirIntervalos(aListaValoresgenerador, intervaloSelect);
        frecuencias = Estadistica.definirTablaDeFrecuencias(intervalos, aListaValoresgenerador);
        int cont = 0;
        for (int i = 0; i < frecuencias.length; i++) {
            cont += frecuencias[i];
        }
        System.out.println("Total: " + cont);
        float frecuenciaEsperada = Estadistica.definirPrecision(((float) Integer.parseInt(muestralinealtext.getText())) / intervaloSelect);
        frecuenciasEsperadas = new float[intervaloSelect];
        for (int i = 0; i < intervaloSelect; i++) {
            frecuenciasEsperadas[i] = frecuenciaEsperada;
        }
        desviaciones = Estadistica.calcularDesviaciones(frecuenciasEsperadas, frecuencias);
        estadisticoPrueba = Estadistica.calcularEstadisticoPrueba(desviaciones);
        return estadisticoPrueba;
    }

    private void generarTablaGenerador() {
        tableLineal = new TableView<>(generateDataInMap());
        tableLineal.setEditable(true);

        iCol = new TableColumn("i");
        iCol.setMinWidth(100);
        iCol.setCellValueFactory(new MapValueFactory<>(Column1MapKey));

        xiCol = new TableColumn("x(i)");
        xiCol.setMinWidth(100);
        xiCol.setCellValueFactory(new MapValueFactory<>(Column2MapKey));

        xi1Col = new TableColumn("Aleatorio");
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
        int semilla = Integer.parseInt(x0linealtext.getText());//95
        int constanteMultiplicador = Integer.parseInt(klinealtext.getText());//31  a = 1+4k
        int incremento = Integer.parseInt(clinealtext.getText());//17
        int exponenteModulo = Integer.parseInt(glinealtext.getText());//653 m=2g

       if (radioMulti.isSelected())
            generador = new GeneradorCongruencialMultiplicativo(semilla, constanteMultiplicador, exponenteModulo);
        if (radioLenguaje.isSelected())
            generador = new GeneradorAleatorioJava();
        else
        generador = new GeneradorCongruencialLineal(semilla, constanteMultiplicador, incremento, exponenteModulo);


        int max = Integer.parseInt(muestralinealtext.getText());
        ObservableList<Map> allData = FXCollections.observableArrayList();
        aListaValoresgenerador = new float[max];
        for (int i = 0; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            String value2 = "" + generador.getSemilla();
            float aleatorio = generador.GenerarAleatorio();
            String value1 = decimalFormat.format(aleatorio);
            aListaValoresgenerador[i] = aleatorio;
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
                else if (radioMulti.isSelected() && (x0%2 == 0)) {
                    System.out.println("x0 no es impar");
                    valido = false;
                }
            }
            if (klinealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int k = Integer.parseInt(klinealtext.getText());
                if (k < 0) {
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
            if (clinealtext.getText().trim().length() == 0) {
                valido = false;
            } else {
                int c = Integer.parseInt(clinealtext.getText());
                int m = (int) Math.pow(2, Integer.parseInt(glinealtext.getText()));
                if (c < 0) {
                    valido = false;
                }
                else if (radioLineal.isSelected() && !validarCoprimos(c, m)){
                    System.out.println( "c y m son coprimos");
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
        URL urlLocation = getClass().getResource("/sample/view/inicio.fxml");
        fxmlLoader.setLocation(urlLocation);

        try {
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Trabajo Practico 1");
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

    /*
     * Busca Maximo comun multiplo entre 2 numeros
     * @return int MCM
     */
    private static int buscarMCM(int number1, int number2) {
        //caso base
        if(number2 == 0){
            return number1;
        }
        return buscarMCM(number2, number1%number2);
    }

    /*
     * Valida si son coprimos 2 numeros
     * @return boolean
     */
    private static boolean validarCoprimos(int number1, int number2) {
        return (buscarMCM(number1, number2) == 1);
    }
}
