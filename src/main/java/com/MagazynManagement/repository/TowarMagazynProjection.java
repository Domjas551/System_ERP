package com.MagazynManagement.repository;

public interface TowarMagazynProjection {
    Long getIdTowaru();
    Long getIdMagazynu();
    String getNazwa();
    String getProducent();
    String getKategoria();
    Integer getIlosc();
}