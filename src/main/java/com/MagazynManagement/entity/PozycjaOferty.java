package com.MagazynManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "towar")
public class PozycjaOferty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nazwa;
    private String producent;
    private String kategoria;

    public PozycjaOferty(String nazwa, String producent, String kategoria) {
        this.nazwa = nazwa;
        this.producent = producent;
        this.kategoria = kategoria;
    }

    public PozycjaOferty() {
        super();
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public String toString() {
        return "pozycjaOferty{" +
                "nazwa='" + nazwa + '\'' +
                ", producent='" + producent + '\'' +
                ", kategoria='" + kategoria + '\'' +
                '}';
    }
}