package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Towar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TowarRepository extends JpaRepository<Towar, Long> {
    Towar findByNazwa(String nazwa);

    @Query(
            value="SELECT t.*, CASE " +
                    "when (Select sum(ilosc) from towar_magazyn where id_towaru=t.id_towaru) is null then 0 " +
                    "else (Select sum(ilosc) from towar_magazyn where id_towaru=t.id_towaru) " +
                    "end as ilosc, " +
                    "case " +
                    "when sum(max_ilosc) is null then 0 " +
                    "else sum(max_ilosc) " +
                    "end as max_ilosc FROM `towar` t " +
                    "left join uzytkownik on t.id_producenta=uzytkownik.id_uzytkownika " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=t.id_towaru " +
                    "where email=?1 group by id_towaru;",
            nativeQuery = true)
    List<Towar> findAllByProducentId(String email);

    @Query(
            value="SELECT t.*, CASE " +
                    "when (Select sum(ilosc) from towar_magazyn where id_towaru=t.id_towaru) is null then 0 " +
                    "else (Select sum(ilosc) from towar_magazyn where id_towaru=t.id_towaru) " +
                    "end as ilosc, " +
                    "case " +
                    "when sum(max_ilosc) is null then 0 " +
                    "else sum(max_ilosc) " +
                    "end as max_ilosc FROM `towar` t " +
                    "left join uzytkownik on t.id_producenta=uzytkownik.id_uzytkownika " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=t.id_towaru " +
                    "where t.id_towaru=?1",
            nativeQuery = true)
    Towar findByIdS(Long idTowaru);

    //funkcja do zmiany danych danego towaru
    @Modifying
    @Query(
            value="UPDATE towar SET nazwa=?2, kategoria=?3 WHERE `towar`.`id_towaru` =?1",
            nativeQuery = true)
    void saveS(Long idTowaru, String nazwa, String kategoria);

    @Query(
            value="SELECT `id_uzytkownika` FROM `uzytkownik` WHERE `czy_producent`=1 and email=?1",
            nativeQuery = true)
    Long findProducentId(String email);

    //funkcja do zapisywania nowgo towaru
    @Modifying
    @Query(
            value="INSERT INTO `towar`( `id_producenta`, `nazwa`, `kategoria`) VALUES (?1,?2,?3)",
            nativeQuery = true)
    void saveT(Long idProducenta, String nazwa, String kategoria);

    @Query(
            value="SELECT id_towaru FROM `towar` where id_producenta=?1 ORDER BY `towar`.`id_towaru` DESC limit 1",
            nativeQuery = true)
    Long findNewesTowarProducenta(Long idProducenta);

    @Query(
            value = "SELECT towar.*,towar_magazyn.ilosc, sum(max_ilosc) as max_ilosc FROM `towar`" +
                    " left join uzytkownik on towar.id_producenta=uzytkownik.id_uzytkownika" +
                    " left join towar_magazyn on towar_magazyn.id_towaru=towar.id_towaru" +
                    " where id_producenta=?1 and id_magazynu=?2 group by id_towaru",
            nativeQuery = true
    )
    List<Towar> findAllInMagazyn(Long idProducenta, Long idMagazynu);

    @Query(
            value="SELECT towar.*, towar_magazyn.ilosc, sum(max_ilosc) as max_ilosc FROM `towar` " +
                    "left join towar_magazyn on towar_magazyn.id_towaru=towar.id_towaru " +
                    "where towar.id_towaru=?1 and id_magazynu=?2",
            nativeQuery = true)
    Towar findByIdInMagazyn(Long idTowaru, Long idMagazynu);

    @Modifying
    @Query(
            value="INSERT INTO dostawa(id_producenta,id_magazynu,data,status,adres) VALUES(?1,?2,?3,'oczekujaca',?4)",
            nativeQuery = true)
    void saveWysylka(Long idProducenta, Long idMagazynu, String data, String adres);

    //funkcja zwracająca id najnowszej dostawy danego producenta
    @Query(
            value="SELECT id_dostawy FROM `dostawa` where id_producenta=?1 ORDER BY `id_dostawy` DESC LIMIT 1",
            nativeQuery = true)
    Long getLastProducentWysylkaId(Long idProducenta);

    @Modifying
    @Query(
            value="INSERT INTO dostawa_towar VALUES(?1,?2,?3) ",
            nativeQuery = true)
    void saveWysylkaTowar(Long idTowaru, Long idDostawy, Long ilosc);

    @Modifying
    @Query(
            value="Update towar_magazyn set ilosc=" +
                    "(select ilosc from towar_magazyn where id_towaru=?1 and id_magazynu=?2)+?3 " +
                    "where id_towaru=?1 and id_magazynu=?2",
            nativeQuery = true)
    void zaktualizujStanTowaruMagazynu(Long idTowaru, Long idMagazynu, Long ilosc);

    @Query(
            value="SELECT * FROM towar where id_towaru=?1",
            nativeQuery = true)
    Towar getTowarById(Long id);
}
