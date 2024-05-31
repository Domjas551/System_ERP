package com.MagazynManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class TowarWysylka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_wysylki;
    private Long id_towaru;
    private int ilosc;

    public TowarWysylka()
    {super();}

    public TowarWysylka(Long id_wysylki, Long id_towaru, int ilosc)
    {
        this.id_wysylki=id_wysylki;
        this.id_towaru=id_towaru;
        this.ilosc=ilosc;
    }

    public Long getId_wysylki() {
        return id_wysylki;
    }

    public void setId_wysylki(Long id_wysylki) {
        this.id_wysylki = id_wysylki;
    }

    public Long getId_towaru() {
        return id_towaru;
    }

    public void setId_towaru(Long id_towaru) {
        this.id_towaru = id_towaru;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    @Override
    public String toString() {
        return "TowarWysylka{" +
                "id_wysylki=" + id_wysylki +
                ", id_towaru=" + id_towaru +
                ", ilosc=" + ilosc +
                '}';
    }
}
