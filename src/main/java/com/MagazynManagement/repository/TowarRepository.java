package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Towar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TowarRepository extends JpaRepository<Towar, Long> {
    Towar findByNazwa(String nazwa);

    @Query(
            value="SELECT towar.*, sum(max_ilosc) as max_ilosc FROM `towar` " +
                    "left join uzytkownik on towar.id_producenta=uzytkownik.id_uzytkownika " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=towar.id_towaru " +
                    "where email=?1",
            nativeQuery = true)
    List<Towar> findAllByProducentId(String email);

    @Query(
            value="SELECT towar.*, sum(max_ilosc) as max_ilosc FROM `towar` " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=towar.id_towaru " +
                    "where towar.id_towaru=?1",
            nativeQuery = true)
    Optional<Towar> findByIdS(Long idTowaru);

    @Modifying
    @Query(
            value="UPDATE towar SET nazwa=?2, kategoria=?3 WHERE `towar`.`id_towaru` =?1",
            nativeQuery = true)
    void saveS(Long idTowaru, String nazwa, String kategoria);

}