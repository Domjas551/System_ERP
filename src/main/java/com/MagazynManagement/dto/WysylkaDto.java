package com.MagazynManagement.dto;

public class WysylkaDto {
    private Long id_wysylki;
    private Long id_klienta_hurtowego;
    private Long id_klienta_detalicznego;
    private Long id_kierowcy;
    private String status;
    private Integer interwal;
    private String data;
    private String adres;
    private int hurtowy = 0;
    private String imie_nazwisko = null;
    private String nazwa_firmy = null;

    public WysylkaDto(Long idWysylki, Long idKlientaHurtowego, Long idKlientaDetalicznego, Long idKierowcy, String status, Integer interwal, String data, String adres, int hurtowy, String imieNazwisko, String nazwaFirmy) {
        id_wysylki = idWysylki;
        id_klienta_hurtowego = idKlientaHurtowego;
        id_klienta_detalicznego = idKlientaDetalicznego;
        id_kierowcy = idKierowcy;
        this.status = status;
        this.interwal = interwal;
        this.data = data;
        this.adres = adres;
        this.hurtowy = hurtowy;
        imie_nazwisko = imieNazwisko;
        nazwa_firmy = nazwaFirmy;
    }

    public WysylkaDto(){
        super();
    }

    public Long getId_wysylki() {
        return id_wysylki;
    }

    public void setId_wysylki(Long id_wysylki) {
        this.id_wysylki = id_wysylki;
    }

    public Long getId_klienta_hurtowego() {
        return id_klienta_hurtowego;
    }

    public void setId_klienta_hurtowego(Long id_klienta_hurtowego) {
        this.id_klienta_hurtowego = id_klienta_hurtowego;
    }

    public Long getId_klienta_detalicznego() {
        return id_klienta_detalicznego;
    }

    public void setId_klienta_detalicznego(Long id_klienta_detalicznego) {
        this.id_klienta_detalicznego = id_klienta_detalicznego;
    }

    public Long getId_kierowcy() {
        return id_kierowcy;
    }

    public void setId_kierowcy(Long id_kierowcy) {
        this.id_kierowcy = id_kierowcy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getInterwal() {
        return interwal;
    }

    public void setInterwal(Integer interwal) {
        this.interwal = interwal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public int getHurtowy() {
        return hurtowy;
    }

    public void setHurtowy(int hurtowy) {
        this.hurtowy = hurtowy;
    }

    public String getImie_nazwisko() {
        return imie_nazwisko;
    }

    public void setImie_nazwisko(String imie_nazwisko) {
        this.imie_nazwisko = imie_nazwisko;
    }

    public String getNazwa_firmy() {
        return nazwa_firmy;
    }

    public void setNazwa_firmy(String nazwa_firmy) {
        this.nazwa_firmy = nazwa_firmy;
    }
}
