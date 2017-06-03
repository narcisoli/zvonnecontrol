package com.example.narcis.zvonnecontrol.obiecte;

/**
 * Created by Narcis on 5/15/2017.
 */

public class coman {
    String text;
    String nume;
    String data;
    String nrdetelefon;
    int id;
    int status;

    public coman(){}
    public coman(String text, String nume, String data, int status, int id,String numar) {
        this.text = text;
        this.nume = nume;
        this.data = data;
        this.status = status;
        this.id=id;
        this.nrdetelefon=numar;
    }

    public String getNrdetelefon() {
        return nrdetelefon;
    }

    public void setNrdetelefon(String nrdetelefon) {
        this.nrdetelefon = nrdetelefon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
