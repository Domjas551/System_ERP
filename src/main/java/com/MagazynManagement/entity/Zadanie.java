package com.MagazynManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Zadanie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zadania")
    public Long id_zadania;

    private Long id_pracownika;

    private Long id_kierownika;

    private String opis = "";

    private String status = "oczekujace";

    public Zadanie(Long id_zadania, Long id_pracownika, Long id_kierownika, String opis, String status){
        this.id_zadania = id_zadania;
        this.id_pracownika = id_pracownika;
        this.id_kierownika = id_kierownika;
        this.opis = opis;
        this.status = status;
    }

    public Zadanie() {
        super();
    }

    public Long getId_zadania(){return id_zadania;}

    public void setId_zadania(Long id_zadania){this.id_zadania = id_zadania;}

    public Long getId_pracownika() {
        return id_pracownika;
    }

    public void setId_pracownika(Long id_pracownika) {
        this.id_pracownika = id_pracownika;
    }

    public Long getId_kierownika() {
        return id_kierownika;
    }

    public void setId_kierownika(Long id_kierownika) {
        this.id_kierownika = id_kierownika;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}