package com.MagazynManagement.dto;

public class TowarDostawaDto {
    private Long idTowaru;
    private Long idMagazynu;
    private Long iloscWDostawie;
    private String adres;

    public TowarDostawaDto(){
        super();
    }
    public TowarDostawaDto(Long idTowaru, Long idMagazynu, Long iloscWDostawie, String adres){
        this.idTowaru=idTowaru;
        this.idMagazynu=idMagazynu;
        this.iloscWDostawie=iloscWDostawie;
        this.adres=adres;
    }

    public Long getIdTowaru() {
        return idTowaru;
    }

    public void setIdTowaru(Long idTowaru) {
        this.idTowaru = idTowaru;
    }

    public Long getIdMagazynu() {
        return idMagazynu;
    }

    public void setIdMagazynu(Long idMagazynu) {
        this.idMagazynu = idMagazynu;
    }

    public Long getIloscWDostawie() {
        return iloscWDostawie;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setIloscWDostawie(Long iloscWDostawie) {
        this.iloscWDostawie = iloscWDostawie;
    }
}
