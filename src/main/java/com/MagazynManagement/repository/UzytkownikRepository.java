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

    @Query(
            value="SELECT * from uzytkownik where email=?1",
            nativeQuery = true)
    Uzytkownik findUserByEmail(String email);

    @Query(
            value="SELECT * from uzytkownik where czy_pracownik=1 and stanowisko='magazynier'",
            nativeQuery = true)
    List<Uzytkownik> findMagazynier();
}
