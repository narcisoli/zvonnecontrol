package com.example.narcis.zvonnecontrol.obiecte;


/**
 * Created by Narcis on 12/19/2016.
 */

public class eveniment {
    String nume;
    String data;
    String detalii;
    long id;
    int tip;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public eveniment() {

    }

    public eveniment(String nume, String data, String detalii, long id, int tip) {
        this.nume = nume;
        this.data = data;
        this.detalii = detalii;
        this.id = id;
        this.tip = tip;
    }

}
