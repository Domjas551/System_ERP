package com.MagazynManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Towar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idTowaru;
    private Long idProducenta;
    private String nazwa;
    private String kategoria;
    private int ilosc;
    private int max_ilosc;


    public Towar(Long idTowaru, Long idProducenta, String nazwa, String kategoria, int ilosc, int max_ilosc) {
        this.idTowaru = idTowaru;
        this.idProducenta = idProducenta;
        this.nazwa = nazwa;
        this.kategoria = kategoria;
        this.ilosc = ilosc;
        this.max_ilosc=max_ilosc;
    }

    public Towar() {
        super();
    }

    public Long getIdTowaru() {
        return idTowaru;
    }

    public String getNazwa() {
        return nazwa;
    }


    public void setIdTowaru(Long idTowaru) {
        this.idTowaru = idTowaru;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Long getIdProducenta() {
        return idProducenta;
    }

    public void setIdProducenta(Long idProducenta) {
        this.idProducenta = idProducenta;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public int getMax_ilosc() {
        return max_ilosc;
    }

    public void setMax_ilosc(int max_ilosc) {
        this.max_ilosc = max_ilosc;
    }
}
