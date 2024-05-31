package com.MagazynManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Komunikat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKomunikatu;
    private Long idNadawcy;
    private Long idOdbiorcy;
    private String tresc;
    private int czyOdczytano;
    private String data;

    public Komunikat(Long idKomunikatu, Long idNadawcy, Long idOdbiorcy, String tresc, int czyOdczytano, String data) {
        this.idKomunikatu = idKomunikatu;
        this.idNadawcy = idNadawcy;
        this.idOdbiorcy = idOdbiorcy;
        this.tresc = tresc;
        this.czyOdczytano = czyOdczytano;
        this.data = data;
    }

    public Komunikat(){super();}

    public Long getIdKomunikatu() {
        return idKomunikatu;
    }

    public void setIdKomunikatu(Long idKomunikatu) {
        this.idKomunikatu = idKomunikatu;
    }

    public Long getIdNadawcy() {
        return idNadawcy;
    }

    public void setIdNadawcy(Long idNadawcy) {
        this.idNadawcy = idNadawcy;
    }

    public Long getIdOdbiorcy() {
        return idOdbiorcy;
    }

    public void setIdOdbiorcy(Long idOdbiorcy) {
        this.idOdbiorcy = idOdbiorcy;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public int getCzyOdczytano() {
        return czyOdczytano;
    }

    public void setCzyOdczytano(int czyOdczytano) {
        this.czyOdczytano = czyOdczytano;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
