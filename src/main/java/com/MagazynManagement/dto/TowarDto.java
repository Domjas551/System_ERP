package com.MagazynManagement.dto;

public class TowarDto {
    private String emailProducenta;
    private String nazwa;
    private String kategoria;

    public TowarDto(){super();};
    public TowarDto(String emailProducenta, String nazwa, String kategoria) {
        this.emailProducenta = emailProducenta;
        this.nazwa = nazwa;
        this.kategoria = kategoria;
    }

    public String getEmailProducenta() {
        return emailProducenta;
    }

    public void setEmailProducenta(String emailProducenta) {
        this.emailProducenta = emailProducenta;
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
}
