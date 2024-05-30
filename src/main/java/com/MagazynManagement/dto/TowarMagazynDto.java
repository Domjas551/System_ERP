package com.MagazynManagement.dto;

public class TowarMagazynDto {
    private Long id;
    private String nazwa_firmy;
    private String nazwa;
    private String kategoria;
    private int ilosc;
    private int max_ilosc;

    public TowarMagazynDto(){super();}
    public TowarMagazynDto(Long id, String nazwaFirmy, String nazwa, String kategoria, int ilosc, int maxIlosc) {
        this.id = id;
        nazwa_firmy = nazwaFirmy;
        this.nazwa = nazwa;
        this.kategoria = kategoria;
        this.ilosc = ilosc;
        max_ilosc = maxIlosc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa_firmy() {
        return nazwa_firmy;
    }

    public void setNazwa_firmy(String nazwa_firmy) {
        this.nazwa_firmy = nazwa_firmy;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
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
