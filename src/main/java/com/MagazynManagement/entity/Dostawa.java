package com.MagazynManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

//klasa dostawy stosowana również jako zadanie dla kierowców
@Entity
public class Dostawa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long idDostawy;
    private String typ;

    private String adres;

    private String status;

    public Dostawa(Long idDostawy, String typ, String adres, String status) {
        this.idDostawy = idDostawy;
        this.typ = typ;
        this.adres = adres;
        this.status = status;
    }

    public Dostawa() {
        super();
    }

    public Long getIdDostawy() {
        return idDostawy;
    }

    public void setIdDostawy(Long idDostawy) {
        this.idDostawy = idDostawy;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
