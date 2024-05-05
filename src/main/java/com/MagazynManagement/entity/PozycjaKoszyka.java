package com.MagazynManagement.entity;

public class PozycjaKoszyka {

    private Towar towar;

    private Integer ilosc;

    public PozycjaKoszyka(Towar towar, int ilosc) {
        this.towar = towar;
        this.ilosc = ilosc;
    }

    public Towar getTowar() {
        return towar;
    }

    public void setTowar(Towar towar) {
        this.towar = towar;
    }

    public Integer getIlosc() {
        return ilosc;
    }

    public void setIlosc(Integer ilosc) {
        this.ilosc = ilosc;
    }
}
