package com.MagazynManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "towar_magazyn")
public class TowarMagazyn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long idMagazynu;
    private long idTowaru;
    private String nazwa;
    private String producent;
    private String kategoria;
    private int ilosc;

    public TowarMagazyn(long idTowaru, long idMagazynu, String nazwa, String producent, String kategoria, int ilosc) {
        this.idMagazynu=idMagazynu;
        this.idTowaru=idTowaru;
        this.nazwa=nazwa;
        this.producent=producent;
        this.kategoria=kategoria;
        this.ilosc=ilosc;
    }

    public TowarMagazyn()
    {super();}

    public long getIdMagazynu() {
        return idMagazynu;
    }

    public void setIdMagazynu(long idMagazynu) {
        this.idMagazynu = idMagazynu;
    }

    public long getIdTowaru() {
        return idTowaru;
    }

    public void setIdTowaru(long idTowaru) {
        this.idTowaru = idTowaru;
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

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    @Override
    public String toString() {
        return idMagazynu+","+idTowaru+","+nazwa+","+producent+","+kategoria+","+ilosc;}
}
