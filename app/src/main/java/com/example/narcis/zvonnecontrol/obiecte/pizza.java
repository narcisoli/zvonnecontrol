package com.example.narcis.zvonnecontrol.obiecte;


public class pizza {

    private String tip;
    private String ingrediente;
    private String gramaj;
    private int pret=0;
    private int nrvoturi=0;
    private int nrcomenzi=0;
    private int nota=0;
    private int nrbucati=0;


    public int getNrvoturi() {
        return nrvoturi;
    }

    public void setNrvoturi(int nrvoturi) {
        this.nrvoturi = nrvoturi;
    }

    public pizza(String tip, String ingrediente, String gramaj, int pret, int nrcomenzi, int nota) {
        this.tip = tip;
        this.ingrediente = ingrediente;
        this.gramaj = gramaj;
        this.pret = pret;
        this.nrcomenzi = nrcomenzi;
        this.nota = nota;
        this.nrbucati=0;
        this.nrvoturi=0;
    }

    public void setNrcomenzi(int nrcomenzi) {
        this.nrcomenzi = nrcomenzi;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }



    public pizza() {
    }




    public String getGramaj() {
        return gramaj;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public int getPret(){return pret;}
    public String getTip() {
        return tip;
    }

    public int getNrcomenzi() {
        return nrcomenzi;
    }

    public int getNota() {
        return nota;
    }

    public int getNrbucati() {
        return nrbucati;
    }

    public void setNrbucati(int nrbucati) {
        this.nrbucati = nrbucati;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public void setGramaj(String gramaj) {
        this.gramaj = gramaj;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }
}