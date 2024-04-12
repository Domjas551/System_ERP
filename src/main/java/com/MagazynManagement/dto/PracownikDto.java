package com.MagazynManagement.dto;

public class PracownikDto {


    private String email;

    private String imie;

    private String nazwisko;

    private String haslo;

    private String telefon;

    private String stanowisko;

    private Float pensja;


    public PracownikDto() {
        super();
    }

    public PracownikDto(String email,
                        String imie,
                        String nazwisko,
                        String haslo,
                        String telefon,
                        String stanowisko,
                        Float pensja) {
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.haslo = haslo;
        this.telefon = telefon;
        this.stanowisko = stanowisko;
        this.pensja = pensja;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    public Float getPensja() {
        return pensja;
    }

    public void setPensja(Float pensja) {
        this.pensja = pensja;
    }
}
