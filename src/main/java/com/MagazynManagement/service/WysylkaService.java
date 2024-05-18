package com.MagazynManagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.MagazynManagement.dto.TowarDto;
import com.MagazynManagement.entity.PozycjaKoszyka;
import com.MagazynManagement.entity.StanMagazynowSesja;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.entity.Wysylka;
import com.MagazynManagement.repository.TowarMagazynRepository;
import com.MagazynManagement.repository.TowarWysylkaRepository;
import com.MagazynManagement.repository.WysylkaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WysylkaService {

    private final WysylkaRepository wysylkaRepository;
    private final TowarMagazynRepository towarMagazynRepository;


    @Transactional
    public void dodajWysylke(Wysylka wysylka){
        wysylkaRepository.dodajWysylke(wysylka.getId_klienta_detalicznego(), wysylka.getId_klienta_hurtowego(), wysylka.getId_kierowcy(), wysylka.getData(), wysylka.getAdres(), wysylka.getStatus());
    }

    public Long findPrzesylkeUzytkownika(Long id_uzytkownika,String data)
    {
        return wysylkaRepository.findPrzesylkeUzytkownika(id_uzytkownika,data);
    }

    public void odejmijTowaryZeStanuMagazynowego(StanMagazynowSesja stany){
        for(int i=0;i<stany.getStany().size();i++)
        {
            for(int j=0;j<stany.getStany().get(i).size();j++) {
                if(stany.getStany().get(i).get(j)!=0);
                {
                    towarMagazynRepository.odejmijTowar(Long.valueOf(i)+1,Long.valueOf(j)+1, stany.getStany().get(i).get(j));
                }
            }
        }
    }
}