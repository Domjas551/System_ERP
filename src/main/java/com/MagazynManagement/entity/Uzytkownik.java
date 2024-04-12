package com.MagazynManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.NaturalId;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Entity
public class Uzytkownik {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUzytkownika;

    @NaturalId(mutable = true)
    private String email;

    private String imie;

    private String nazwisko;

    private String haslo;

    private String telefon;

    private String stanowisko;

    private String nazwaFirmy;

    private Float pensja;

    private boolean czyAktywny;

    private boolean czyPracownik;

    private boolean czyProducent;

    private boolean czyKlientDetaliczny;

    private boolean czyKlientHurtowy;


    public Uzytkownik() {
        super();
    }

    public Uzytkownik(String email,
                      String imie,
                      String nazwisko,
                      String haslo,
                      String telefon,
                      String stanowisko,
                      String nazwaFirmy,
                      Float pensja,
                      boolean czyAktywny,
                      boolean czyPracownik,
                      boolean czyProducent,
                      boolean czyKlientDetaliczny,
                      boolean czyKlientHurtowy) {
        this.email = email;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.haslo = haslo;
        this.telefon = telefon;
        this.stanowisko = stanowisko;
        this.nazwaFirmy = nazwaFirmy;
        this.pensja = pensja;
        this.czyAktywny = czyAktywny;
        this.czyPracownik = czyPracownik;
        this.czyProducent = czyProducent;
        this.czyKlientDetaliczny = czyKlientDetaliczny;
        this.czyKlientHurtowy = czyKlientHurtowy;
    }


    public String getRola(){
        if(isCzyPracownik()){
            switch (getStanowisko()){
                case "kierownik magazynu":
                    return "MANAGER";

                case "magazynier":
                    return "MAGAZYNIER";

                case "kierowca":
                    return "KIEROWCA";

                case "koordynator":
                    return "KOORDYNATOR";

                case "administrator":
                    return "ADMIN";
            }
        } else if (isCzyProducent()) {
            return "PRODUCENT";
        }

        return "USER";
    }


    public Long getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(Long idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
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

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }

    public boolean isCzyAktywny() {
        return czyAktywny;
    }

    public void setCzyAktywny(boolean czyAktywny) {
        this.czyAktywny = czyAktywny;
    }

    public boolean isCzyPracownik() {
        return czyPracownik;
    }

    public void setCzyPracownik(boolean czyPracownik) {
        this.czyPracownik = czyPracownik;
    }

    public boolean isCzyProducent() {
        return czyProducent;
    }

    public void setCzyProducent(boolean czyProducent) {
        this.czyProducent = czyProducent;
    }

    public void setPensja(Float pensja) {
        this.pensja = pensja;
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
}
