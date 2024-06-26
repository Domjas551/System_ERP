package com.MagazynManagement.service;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PracownikService {

    private final PracownikRepository pracownikRepository;

    private final MagazynRepository magazynRepository;

    private final KontoRepository kontoRepository;

    private final ZadanieRepozytory zadanieRepozytory;

    private final DostawaRepository dostawaRepository;

    private final ZamowienieRepository zamowienieRepository;

    public List<Pracownik> getAllPracownik(){
        return pracownikRepository.findAll();
    }

    @Transactional
    public void usunPracownika(Long idPracownika){
        Pracownik pracownik = pracownikRepository.findById(idPracownika)
                .orElseThrow(() -> new EntityNotFoundException("Taki pracownik nie istnieje"));
        kontoRepository.deleteByPracownik(pracownik);
        pracownikRepository.deleteById(idPracownika);
    }

    public Pracownik findByEmail(String email){
        return pracownikRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Pracownik nie znaleziony"));
    }

    public Pracownik getPracownikById(Long idPracownika){
        return pracownikRepository.findById(idPracownika)
                .orElseThrow(() -> new EntityNotFoundException("Pracownik nie znaleziony"));
    }

    public void aktualizujPracownika(Pracownik pracownik){
        Long idPracownika = pracownik.getIdPracownika();
        if(pracownikRepository.existsById(idPracownika))
            pracownikRepository.save(pracownik);
        else
            throw new EntityNotFoundException("Pracownik nie znaleziony");
    }
}
