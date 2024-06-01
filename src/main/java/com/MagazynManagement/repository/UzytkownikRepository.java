package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.entity.Zadanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {

    Optional<Uzytkownik> findByEmail(String email);

    Optional<Uzytkownik> findById(Long id);

    @Query(
            value="SELECT * from uzytkownik where email=?1",
            nativeQuery = true)
    Uzytkownik findUserByEmail(String email);

    @Query(
            value="SELECT * from uzytkownik inner join pracownik_magazyn on id_uzytkownika=id_pracownika where czy_pracownik=1 and stanowisko='magazynier' and id_magazynu=?1",
            nativeQuery = true)
    List<Uzytkownik> findMagazynier(Long magazyn);

    @Query(
            value="SELECT * from magazyn where id_kierownika=?1",
            nativeQuery = true)
    Long findMagazynByKierownik(Long kierownik);

    @Query(
            value="SELECT id_uzytkownika from uzytkownik where email=?1 and czy_pracownik=1",
            nativeQuery = true)
    Long findKierownik(String email);

    @Query(
            value="SELECT * from uzytkownik where id_uzytkownika=?1",
            nativeQuery = true)
    Uzytkownik findUzytkownikByID(Long id);

    @Query(
            value="SELECT * from uzytkownik where stanowisko='kierowca'",
            nativeQuery = true)
    List<Uzytkownik> findKierowcy();
}
