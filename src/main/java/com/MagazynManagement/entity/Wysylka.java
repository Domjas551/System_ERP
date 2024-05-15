package com.MagazynManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Wysylka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_wysylki;

    private Long id_klienta_hurtowego;
    private Long id_klienta_detalicznego;
    private Long id_kierowcy;
    private String status;
    private int interwal;
    private String data;
    private String adres;


    public Wysylka(Long id_wysylki, Long id_klienta_hurtowego, Long id_klienta_detalicznego, Long id_kierowcy, String status, int interwal, String data, String adres)
    {
        this.id_wysylki=id_wysylki;
        this.id_klienta_detalicznego=id_klienta_detalicznego;
        this.id_klienta_hurtowego=id_klienta_hurtowego;
        this.id_kierowcy=id_kierowcy;
        this.status=status;
        this.interwal=interwal;
        this.data=data;
        this.adres=adres;

    }

    public Long getId_wysylki() {
        return id_wysylki;
    }

    public void setId_wysylki(Long id_wysylki) {
        this.id_wysylki = id_wysylki;
    }

    public Long getId_klienta_hurtowego() {
        return id_klienta_hurtowego;
    }

    public void setId_klienta_hurtowego(Long id_klienta_hurtowego) {
        this.id_klienta_hurtowego = id_klienta_hurtowego;
    }

    public Long getId_klienta_detalicznego() {
        return id_klienta_detalicznego;
    }

    public void setId_klienta_detalicznego(Long id_klienta_detalicznego) {
        this.id_klienta_detalicznego = id_klienta_detalicznego;
    }

    public Long getId_kierowcy() {
        return id_kierowcy;
    }

    public void setId_kierowcy(Long id_kierowcy) {
        this.id_kierowcy = id_kierowcy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInterwal() {
        return interwal;
    }

    public void setInterwal(int interwal) {
        this.interwal = interwal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "Wysylka{" +
                "id_wysylki=" + id_wysylki +
                ", id_klienta_hurtowego=" + id_klienta_hurtowego +
                ", id_klienta_detalicznego=" + id_klienta_detalicznego +
                ", id_kierowcy=" + id_kierowcy +
                ", status='" + status + '\'' +
                ", interwal=" + interwal +
                ", data='" + data + '\'' +
                ", adres='" + adres + '\'' +
                '}';
    }
}
