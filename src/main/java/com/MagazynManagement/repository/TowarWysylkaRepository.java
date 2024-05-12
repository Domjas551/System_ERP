package com.MagazynManagement.repository;

import com.MagazynManagement.entity.TowarWysylka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TowarWysylkaRepository extends JpaRepository<TowarWysylka, Long>
{
    @Modifying
    @Query(
            value="INSERT INTO `wysylka_towar` (`id_towaru`,`id_wysylki`,`ilosc`) Values (?1,?2,?3);",
            nativeQuery = true)
    void dodajTowarWysylke(Long id_towaru, Long id_wysylki, int ilosc);

}
