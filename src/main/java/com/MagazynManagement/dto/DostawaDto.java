package com.MagazynManagement.dto;

import java.util.Date;

public class DostawaDto {

    private Long id_dostawy;
    private Long id_magazynu;
    private Long id_producenta;
    private String data;
    private Long id_kierowcy;
    private String adres;
    private String status;
    private String nazwa_firmy;
    private String adres_magazynu;

    public DostawaDto(Long idDostawy, String typ, Long idDostawy1, Long idMagazynu, Long idProducenta, String data, Long idKierowcy, String adres, String status, String nazwa_firmy, String adres_magazynu) {
        id_dostawy = idDostawy1;
        id_magazynu = idMagazynu;
        id_producenta = idProducenta;
        this.data = data;
        id_kierowcy = idKierowcy;
        this.adres = adres;
        this.status = status;
        this.nazwa_firmy = nazwa_firmy;
        this.adres_magazynu = adres_magazynu;
    }

    public DostawaDto() {
        super();
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

    public String getAdres_magazynu() {
        return adres_magazynu;
    }

    public void setAdres_magazynu(String adres_magazynu) {
        this.adres_magazynu = adres_magazynu;
    }

    public String getNazwa_firmy() {
        return nazwa_firmy;
    }

    public void setNazwa_firmy(String nazwa_firmy) {
        this.nazwa_firmy = nazwa_firmy;
    }

    public Long getId_kierowcy() {
        return id_kierowcy;
    }

    public void setId_kierowcy(Long id_kierowcy) {
        this.id_kierowcy = id_kierowcy;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId_producenta() {
        return id_producenta;
    }

    public void setId_producenta(Long id_producenta) {
        this.id_producenta = id_producenta;
    }

    public Long getId_magazynu() {
        return id_magazynu;
    }

    public void setId_magazynu(Long id_magazynu) {
        this.id_magazynu = id_magazynu;
    }

    public Long getId_dostawy() {
        return id_dostawy;
    }

    public void setId_dostawy(Long id_dostawy) {
        this.id_dostawy = id_dostawy;
    }
}
