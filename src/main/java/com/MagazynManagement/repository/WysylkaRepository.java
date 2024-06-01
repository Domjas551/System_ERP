package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Wysylka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WysylkaRepository extends JpaRepository<Wysylka, Long>
{

    @Modifying
    @Query(
            value="INSERT INTO `wysylka` (`id_klienta_detalicznego`,`id_klienta_hurtowego`,`id_kierowcy`,`data`,`interwal`,`adres`,`status`) VALUES (?1,?2,?3,?4,?5,?6,?7);",
            nativeQuery = true)
    void dodajWysylke(Long id_klienta_detalicznego, Long id_klienta_hurtowego, Long id_kierowcy, String data, Integer interwal, String adres, String status);

    @Modifying
    @Query(
            value="update wysylka set interwal=null where `id_wysylki`=:idWysylki",
            nativeQuery = true)
    void nullifyInterwal(@Param("idWysylki") Long idWysylki);

    @Query(
            value = "SELECT id_wysylki FROM `wysylka` WHERE (`id_klienta_hurtowego`=:idUzytkownika or `id_klienta_detalicznego`=:idUzytkownika) and `data`=:data",
            nativeQuery = true
    )
    Long findPrzesylkeUzytkownika(@Param("idUzytkownika") Long idUzytkownika, @Param("data") String data);

    @Query(
            value="SELECT * FROM wysylka where (`id_klienta_hurtowego`=:idUzytkownika or `id_klienta_detalicznego`=:idUzytkownika)", nativeQuery = true)
    List<Wysylka> findWysylkaByIdKlienta(@Param("idUzytkownika") Long idUzytkownika);

    @Query(
            value="SELECT * FROM wysylka where interwal is not null", nativeQuery = true)
    List<Wysylka> findWysylkaCzykliczna();

    @Query(value = "Select * from wysylka where id_kierowcy is null and status = 'niezatwierdzona'", nativeQuery = true)
    List<Wysylka> getWysylkaBezKierowcy();

    @Query(value = "Select * from wysylka where id_wysylki = ?1", nativeQuery = true)
    Wysylka getByWysylkaId(Long id);
}
