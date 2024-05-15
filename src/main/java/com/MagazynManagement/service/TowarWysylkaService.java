package com.MagazynManagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.MagazynManagement.dto.TowarDto;
import com.MagazynManagement.entity.PozycjaKoszyka;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.repository.TowarWysylkaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TowarWysylkaService {

    private final TowarWysylkaRepository towarWysylkaRepository;


    @Transactional
    public void dodajTowarWysylki(List<PozycjaKoszyka> koszyk,Long id_wysylki)
    {
        for(int i=0; i<koszyk.size();i++)
        {
            towarWysylkaRepository.dodajTowarWysylke(koszyk.get(i).getTowar().getIdTowaru(),id_wysylki,koszyk.get(i).getIlosc());
        }

    }
}
