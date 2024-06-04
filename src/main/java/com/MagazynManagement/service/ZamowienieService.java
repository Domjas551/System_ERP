package com.MagazynManagement.service;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZamowienieService {

    private final ZamowienieRepository zamowienieRepository;

    private final DostawaRepository dostawaRepository;

    private final SkladZamowieniaRepository skladZamowieniaRepository;

    private final ZadanieRepozytory zadanieRepozytory;

    private final TowarMagazynRepository towarMagazynRepository;

    public List<Zamowienie> znajdzZamowienieKlienta(Klient klient){
        return zamowienieRepository.findByKlient(klient);
    }

    public Zamowienie znajdzZamowienieById(Long id){
        return zamowienieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego zamowienia"));
    }

    public List<Zamowienie> getAllZamowienia(){
        return zamowienieRepository.findAll();
    }

    public void odejmijMaterialyZeStanuMagazynowego(List<PozycjaKoszyka> koszyk){
        for(PozycjaKoszyka pozycja : koszyk){
            Towar towar = pozycja.getTowar();
            int iloscDoOdjecia = pozycja.getIlosc();

            //towarMagazynRepository.odejmijTowar(towar.getIdTowaru(), iloscDoOdjecia);
        }
    }
}
