package com.MagazynManagement.service;

import com.MagazynManagement.entity.PozycjaKoszyka;
import com.MagazynManagement.entity.PozycjaOferty;
import com.MagazynManagement.entity.StanMagazynowSesja;
import com.MagazynManagement.entity.TowarMagazyn;
import com.MagazynManagement.repository.PozycjaOfertyRepository;
import com.MagazynManagement.repository.TowarMagazynRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PozycjaOfertyService {

    private final PozycjaOfertyRepository pozycjaOfertyRepository;

    public List<PozycjaOferty> pobierzOferte(){
        List<Object[]> results = pozycjaOfertyRepository.getOferta();
        List<PozycjaOferty> pozycjaOfertyList = new ArrayList<>();
        for (Object[] result : results) {
            String nazwa = (String) result[0];
            String producent = (String) result[1];
            String kategoria = (String) result[2];
            pozycjaOfertyList.add(new PozycjaOferty(nazwa, producent, kategoria));
        }
        return pozycjaOfertyList;
    }
}
