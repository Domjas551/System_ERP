package com.MagazynManagement.dto;

public class UserDto {

    private int id;

    private String email;

    private String imie;

    private String nazwisko;

    private String nazwaFirmy;

    private String haslo;

    private String telefon;

    private boolean czyKlientDetaliczny;

    private boolean czyKlientHurtowy;

    public UserDto() {
        super();
    }

    public UserDto(int id,
                   String email,
                   String imie,
                   String nazwisko,
                   String nazwaFirmy,
                   String haslo,
                   String telefon,
                   boolean czyKlientDetaliczny,
                   boolean czyKlientHurtowy) {
        this.id=id;
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nazwaFirmy = nazwaFirmy;
        this.haslo = haslo;
        this.telefon = telefon;
        this.czyKlientDetaliczny = czyKlientDetaliczny;
        this.czyKlientHurtowy = czyKlientHurtowy;
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

    public boolean isCzyKlientDetaliczny() {
        return czyKlientDetaliczny;
    }

    public void setCzyKlientDetaliczny(boolean czyKlientDetaliczny) {
        this.czyKlientDetaliczny = czyKlientDetaliczny;
    }

    public boolean isCzyKlientHurtowy() {
        return czyKlientHurtowy;
    }

    public void setCzyKlientHurtowy(boolean czyKlientHurtowy) {
        this.czyKlientHurtowy = czyKlientHurtowy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }
}
