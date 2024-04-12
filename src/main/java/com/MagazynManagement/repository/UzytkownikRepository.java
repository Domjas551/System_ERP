package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {

    Optional<Uzytkownik> findByEmail(String email);
}
