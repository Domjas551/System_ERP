package com.MagazynManagement.dto;

public class TowarDostawaDto {
    private Long idTowaru;
    private Long idMagazynu;
    private Long iloscWDostawie;

    public TowarDostawaDto(){
        super();
    }
    public TowarDostawaDto(Long idTowaru, Long idMagazynu, Long iloscWDostawie){
        this.idTowaru=idTowaru;
        this.idMagazynu=idMagazynu;
        this.iloscWDostawie=iloscWDostawie;
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

    public void setIloscWDostawie(Long iloscWDostawie) {
        this.iloscWDostawie = iloscWDostawie;
    }
}
