package com.example.narcis.zvonnecontrol.obiecte;

/**
 * Created by Narcis on 12/4/2016.
 */

public class pizza {

    private String tip;
    private String ingrediente;
    private String gramaj;
    private int pret=0;
    private int nrvoturi=0;
    private int nrcomenzi=0;
    private float nota=0;
    private int nrbucati=0;


    public pizza(String tip, String ingrediente, String gramaj, int pret, int nrcomenzi, float nota) {
        this.tip = tip;
        this.ingrediente = ingrediente;
        this.gramaj = gramaj;
        this.pret = pret;
        this.nrcomenzi = nrcomenzi;
        this.nota = nota;
        this.nrbucati=0;
        this.nrvoturi=0;
    }

    public pizza() {
    }

    public int getNrvoturi() {
        return nrvoturi;
    }

    public void setNrvoturi(int nrvoturi) {
        this.nrvoturi = nrvoturi;
    }

    public String getGramaj() {
        return gramaj;
    }

    public void setGramaj(String gramaj) {
        this.gramaj = gramaj;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getPret(){return pret;}

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getNrcomenzi() {
        return nrcomenzi;
    }

    public void setNrcomenzi(int nrcomenzi) {
        this.nrcomenzi = nrcomenzi;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getNrbucati() {
        return nrbucati;
    }

    public void setNrbucati(int nrbucati) {
        this.nrbucati = nrbucati;
    }
}