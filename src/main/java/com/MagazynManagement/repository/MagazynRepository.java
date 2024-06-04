package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Magazyn;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazynRepository extends JpaRepository<Magazyn, Long> {

    @Query(
            value="SELECT distinct(id_magazynu) FROM `towar_magazyn` NATURAL join towar where id_producenta=?1 order by id_magazynu asc",
            nativeQuery = true)
    List<String> findByProducentId(Long idProducenta);

    @Query(
            value="SELECT id_magazynu FROM `magazyn`",
            nativeQuery = true)
    List<String> findAllMagazyn();


    @Modifying
    @Query(
            value="Insert into `towar_magazyn` VALUES(?1,?2,0,0)",
            nativeQuery = true)
    void addTowarToMagazyn(Long idTowaru,Long idMagazynu);

    @Query(value = "Select adres from magazyn where id_magazynu = ?1", nativeQuery = true)
    String getAdres(Long id);
}
