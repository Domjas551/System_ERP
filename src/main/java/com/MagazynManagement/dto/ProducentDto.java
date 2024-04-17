package com.MagazynManagement.dto;

public class ProducentDto {

    private String email;

    private String haslo;

    private String telefon;

    private String nazwaFirmy;
    private int id;


    public ProducentDto() {
        super();
    }

    public ProducentDto(String email,
                        String haslo,
                        String telefon,
                        String nazwaFirmy,
                        int id) {
        this.email = email;
        this.haslo = haslo;
        this.telefon = telefon;
        this.nazwaFirmy = nazwaFirmy;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }

    public int getId() {
        return id;
    }

    public void setId(int idProd) {
        this.id = idProd;
    }
}
