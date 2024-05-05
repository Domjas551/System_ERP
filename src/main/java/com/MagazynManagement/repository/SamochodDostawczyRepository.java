package com.MagazynManagement.repository;

import com.MagazynManagement.entity.SamochodDostawczy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SamochodDostawczyRepository extends JpaRepository<SamochodDostawczy, Long>{
}
