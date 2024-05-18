package com.MagazynManagement.repository;

import com.MagazynManagement.entity.PozycjaOferty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PozycjaOfertyRepository extends JpaRepository<PozycjaOferty, Long> {

    @Query(
            value="SELECT distinct towar.nazwa, uzytkownik.nazwa_firmy as 'producent', towar.kategoria FROM towar " +
                    "left join uzytkownik on towar.id_producenta=uzytkownik.id_uzytkownika " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=towar.id_towaru;"
            , nativeQuery = true)
    List<Object[]> getOferta();

}
