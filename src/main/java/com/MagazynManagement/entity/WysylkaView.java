package com.MagazynManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

public class WysylkaView {

    private Wysylka wysylka;
    private String zawartosc;


    public WysylkaView(Wysylka wysylka, String zawartosc)
    {
        this.wysylka=wysylka;
        this.zawartosc=zawartosc;
    }

    public Wysylka getWysylka() {
        return wysylka;
    }

    public void setWysylka(Wysylka wysylka) {
        this.wysylka = wysylka;
    }

    public String getZawartosc() {
        return zawartosc;
    }

    public void setZawartosc(String zawartosc) {
        this.zawartosc = zawartosc;
    }

    @Override
    public String toString() {
        return "WysylkaView{" +
                "wysylka=" + wysylka +
                ", zawartosc='" + zawartosc + '\'' +
                '}';
    }
}
