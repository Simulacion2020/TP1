package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class Data {

    private final SimpleStringProperty i;
    private final SimpleStringProperty ri;


    public Data(String i, String ri) {
        this.i = new SimpleStringProperty(i);
        this.ri = new SimpleStringProperty(ri);
    }

    public String getI() {
        return i.get();
    }

    public SimpleStringProperty iProperty() {
        return i;
    }

    public void setI(String i) {
        this.i.set(i);
    }

    public String getRi() {
        return ri.get();
    }

    public SimpleStringProperty riProperty() {
        return ri;
    }

    public void setRi(String ri) {
        this.ri.set(ri);
    }
}