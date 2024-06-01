package com.MagazynManagement.repository;

import com.MagazynManagement.dto.DostawaDto;
import com.MagazynManagement.entity.Dostawa;
import com.MagazynManagement.entity.Towar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DostawaRepository extends JpaRepository<Dostawa, Long> {

    @Query(
            value="SELECT `id_uzytkownika` FROM `uzytkownik` WHERE `stanowisko`='kierowca' and email=?1",
            nativeQuery = true)
    Long findKierowcaId(String email);

    @Query(
            value="Select row_number() over (order by t.id_dostawy) as id, t.* from " +
                    "(SELECT id_dostawy , 'dostawa' as typ, adres, status FROM `dostawa` " +
                    "where status='oczekujaca' and id_kierowcy=?1 " +
                    "union select id_wysylki as id, 'wysylka' as typ, adres, status from wysylka " +
                    "where status='oczekujaca' and id_kierowcy=?1) t;",
            nativeQuery = true)
    List<Dostawa> findAllActiveByKierowcaId(Long idKierowcy);

    @Query(
            value="Select row_number() over (order by t.id_dostawy) as id, t.* from " +
            "(SELECT id_dostawy , 'dostawa' as typ, adres, status FROM `dostawa` " +
            "where (status='zrealizowana' or status='niezrealizowana') and id_kierowcy=?1 " +
            "union select id_wysylki as id, 'wysylka' as typ, adres, status from wysylka " +
            "where (status='zrealizowana' or status='niezrealizowana') and id_kierowcy=?1) t;",
            nativeQuery = true)
    List<Dostawa> findAllNotActiveByKierowcaId(Long idKierowcy);

    @Query(
            value="select id_wysylki as id, id_wysylki as id_dostawy, 'wysylka' as typ, " +
                    "adres, status from wysylka where status='oczekujaca' and id_wysylki=?1",
            nativeQuery = true)
    Dostawa findWysylkaById(Long id);

    @Query(
            value="select id_dostawy as id, id_dostawy, 'dostawa' as typ, " +
                    "adres, status from dostawa where status='oczekujaca' and id_dostawy=?1",
            nativeQuery = true)
    Dostawa findDostawaById(Long id);

    @Query(
            value="select id_wysylki as id, id_wysylki as id_dostawy, 'wysylka' as typ, " +
                    "adres, status from wysylka where (status='zrealizowana' or status='niezrealizowana') and id_wysylki=?1",
            nativeQuery = true)
    Dostawa findNieaktywnaWysylkaById(Long id);

    @Query(
            value="select id_dostawy as id, id_dostawy, 'dostawa' as typ, " +
                    "adres, status from dostawa where (status='zrealizowana' or status='niezrealizowana') and id_dostawy=?1",
            nativeQuery = true)
    Dostawa findNieaktywnaDostawaById(Long id);

    @Modifying
    @Query(
            value="UPDATE wysylka SET status=?2 WHERE `wysylka`.`id_wysylki` =?1",
            nativeQuery = true)
    void zapiszStatusWysylka(Long idWysylki, String status);

    @Modifying
    @Query(
            value="UPDATE dostawa SET status=?2 WHERE `dostawa`.`id_dostawy`=?1",
            nativeQuery = true)
    void zapiszStatusDostawa(Long idDostawy, String status);

    @Query(
            value="SELECT id_dostawy from dostawa where id_kierowcy is null and id_magazynu = ?1",
            nativeQuery = true)
    List<Long> findDostawaBezKierowcy(Long id);

    @Query(
            value="SELECT id_producenta from dostawa where id_dostawy=?1",
            nativeQuery = true)
    Long findDostawaProducent(Long id);

    @Query(
            value="SELECT adres from dostawa where id_dostawy=?1",
            nativeQuery = true)
    String findDostawaAdres(Long id);

    @Query(
            value="SELECT data from dostawa where id_dostawy=?1",
            nativeQuery = true)
    String findDostawaData(Long id);

    @Query(
            value="SELECT id_kierowcy from dostawa where id_dostawy=?1",
            nativeQuery = true)
    Long findDostawaKierowca(Long id);

    @Modifying
    @Transactional
    @Query(
            value="UPDATE dostawa SET status='oczekujaca', id_kierowcy=?1 WHERE id_dostawy = ?2",
            nativeQuery = true)
    void dodajKierowce(Long id_kierowcy, Long id_dostawy);
}
