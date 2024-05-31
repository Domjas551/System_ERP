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
            value="INSERT INTO `wysylka` (`id_klienta_detalicznego`,`id_klienta_hurtowego`,`id_kierowcy`,`data`,`interwal`,`adres`,`status`) VALUES (?1,?2,?3,?4,NULL,?5,?6);",
            nativeQuery = true)
    void dodajWysylke(Long id_klienta_detalicznego, Long id_klienta_hurtowego, Long id_kierowcy, String data, String adres, String status);

    @Query(
            value = "SELECT id_wysylki FROM `wysylka` WHERE (`id_klienta_hurtowego`=:idUzytkownika or `id_klienta_detalicznego`=:idUzytkownika) and `data`=:data",
            nativeQuery = true
    )
    Long findPrzesylkeUzytkownika(@Param("idUzytkownika") Long idUzytkownika, @Param("data") String data);

    @Query(
            value="SELECT * FROM wysylka where (`id_klienta_hurtowego`=:idUzytkownika or `id_klienta_detalicznego`=:idUzytkownika)", nativeQuery = true)
    List<Wysylka> findWysylkaByIdKlienta(@Param("idUzytkownika") Long idUzytkownika);

}
