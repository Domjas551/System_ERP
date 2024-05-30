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
    List<Object[]> findByMagazyn_IdMagazynu(Long idMagazynu);

    @Query(
            value = "SELECT ilosc FROM towar_magazyn WHERE id_magazynu = :idMagazynu AND id_towaru = :idTowaru",
            nativeQuery = true
    )
    int findIloscTowarMagazyn(@Param("idMagazynu") Long idMagazynu, @Param("idTowaru") Long idTowaru);

    @Modifying
    @Transactional
    @Query(value = "UPDATE towar_magazyn SET ilosc = ilosc - ?3 WHERE id_towaru = ?2 and id_magazynu=?1", nativeQuery = true)
    void odejmijTowar(Long MagazynId, Long TowarId, int ilosc);


    @Query(value = "SELECT max_ilosc from towar_magazyn where id_magazynu = ?1 and id_towaru = ?2", nativeQuery = true)
    int getMaxIlosc(Long id_m, Long id_p);
}
