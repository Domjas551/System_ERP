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

    /*public void zlozNoweZamowienie(float kwota, List<PozycjaKoszyka> koszyk, AdresDostawy adresDostawy){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Klient klient = userDetails.getKonto().getKlient();

        Dostawa dostawa = new Dostawa();
        dostawa.setAdres(adresDostawy.getAdres());
        dostawa.setStatus("W realizacji");
        Date obecnaData = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(obecnaData);
        calendar.add(Calendar.DATE, 3);
        dostawa.setOczekiwanaDataDostawy(calendar.getTime());
        dostawaRepository.save(dostawa);

        Zamowienie zamowienie = new Zamowienie();
        zamowienie.setKwota(kwota);
        zamowienie.setDataZlozenia(new Date());
        zamowienie.setDostawa(dostawa);
        zamowienie.setKlient(klient);
        zamowienieRepository.save(zamowienie);

        Rozpiska rozpiska = new Rozpiska();
        rozpiska.setAdres(adresDostawy.getAdres());
        rozpiska.setWykonane(0);
        rozpiska.setData(calendar.getTime());
        rozpiska.setNr_zamowienia(zamowienie.getNrZamowienia());
        rozpiskaRepozytory.save(rozpiska);

        for (PozycjaKoszyka pozycjaKoszyka : koszyk){
            Material material = pozycjaKoszyka.getMaterial();
            int ilosc = pozycjaKoszyka.getIlosc();

            SkladZamowieniaId skladZamowieniaId = new SkladZamowieniaId(zamowienie.getNrZamowienia(), material.getIdTowaru());
            SkladZamowienia skladZamowienia = new SkladZamowienia(skladZamowieniaId,
                    zamowienie,
                    material,
                    ilosc);
            *//*skladZamowienia.setZamowienie(zamowienie);
            skladZamowienia.setMaterial(material);
            skladZamowienia.setIlosc(ilosc);*//*
            skladZamowieniaRepository.save(skladZamowienia);
        }
    }*/

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
