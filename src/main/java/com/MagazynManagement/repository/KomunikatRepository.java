package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Komunikat;
import com.MagazynManagement.entity.Towar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KomunikatRepository extends JpaRepository<Komunikat, Long> {

    @Query(
            value="SELECT * FROM `komunikat` WHERE id_odbiorcy=?1 and czy_odczytano=0",
            nativeQuery = true)
    List<Komunikat> findAllUnreadKomunikat(Long idProducenta);

    @Query(
            value="SELECT * FROM `komunikat` WHERE id_odbiorcy=?1 and czy_odczytano=1",
            nativeQuery = true)
    List<Komunikat> findAllReadKomunikat(Long idProducenta);

    @Query(
            value="SELECT * FROM `komunikat` WHERE id_komunikatu=?1",
            nativeQuery = true)
    Komunikat findKomunikat(Long idKomunikatu);

    @Modifying
    @Query(
            value="UPDATE komunikat SET czy_odczytano=1 WHERE id_komunikatu=?1",
            nativeQuery = true)
    void zmienNaPrzeczytano(Long idKomunikatu);
}
