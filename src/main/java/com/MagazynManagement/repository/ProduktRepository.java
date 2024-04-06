package com.MagazynManagement.repository;

import com.MagazynManagement.entity.Material;
import com.MagazynManagement.entity.Produkt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Long> {
    Produkt findByNazwa(String nazwa);

}
