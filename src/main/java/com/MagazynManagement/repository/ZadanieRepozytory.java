package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Zadanie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZadanieRepozytory extends JpaRepository<Zadanie, Long>{

    @Query(
            value="SELECT * from zadanie where id_pracownika=?1 and status = 'oczekujace'",
            nativeQuery = true)
    List<Zadanie> findByIdPracownika(Long id_pracownika);

    @Query(value = "SELECT * from zadanie where id_kierownika=?1 and status = 'oczekujace'", nativeQuery = true)
    List<Zadanie> findByKierownik(Long id_kierownika);

    @Query(value = "UPDATE zadanie SET status='anulowane' where id_zadania=?1", nativeQuery = true)
    void anulujZadanie(Long id);
}
