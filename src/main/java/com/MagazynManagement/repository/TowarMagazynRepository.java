package com.MagazynManagement.repository;

import com.MagazynManagement.entity.TowarMagazyn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface TowarMagazynRepository extends JpaRepository<TowarMagazyn, Long> {

    @Query(
            value="SELECT towar_magazyn.id_towaru, towar_magazyn.id_magazynu, towar.nazwa, uzytkownik.nazwa_firmy as 'producent', towar.kategoria, towar_magazyn.ilosc FROM towar " +
                    "left join uzytkownik on towar.id_producenta=uzytkownik.id_uzytkownika " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=towar.id_towaru " +
                    "where towar_magazyn.id_magazynu=?1", nativeQuery = true)
    List<TowarMagazynProjection> findByMagazyn_IdMagazynu(Long idMagazynu);

    @Modifying
    @Transactional
    @Query(value = "UPDATE stanmagazynu SET ilosc = ilosc - :ilosc WHERE id_towaru = :TowarId", nativeQuery = true)
    void odejmijTowar(@Param("TowarId") Long TowarId, @Param("ilosc") int ilosc);
}
