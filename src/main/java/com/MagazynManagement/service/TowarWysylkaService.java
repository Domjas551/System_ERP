package com.MagazynManagement.service;

import com.MagazynManagement.entity.PozycjaKoszyka;
import com.MagazynManagement.entity.TowarWysylka;
import com.MagazynManagement.repository.TowarWysylkaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Transactional
    public void dodajTowarWysylkiCykl(List<TowarWysylka> tw,Long id_wysylki)
    {
        for(int i=0; i<tw.size();i++)
        {
            towarWysylkaRepository.dodajTowarWysylke(tw.get(i).getId_towaru(),id_wysylki,tw.get(i).getIlosc());
        }

    }

    public List<TowarWysylka> findTowarIdByWysylkaID(Long id_wysylki)
    {
        List<Object[]> results = towarWysylkaRepository.findTowarByWysylkaID(id_wysylki);
        List<TowarWysylka> towarWysylkaList = new ArrayList<>();
        for (Object[] result : results) {
            Integer idTowaru = (Integer) result[0];
            Integer idWysylki = (Integer) result[1];
            Integer ilosc = (Integer) result[2];
            towarWysylkaList.add(new TowarWysylka(idWysylki.longValue(), idTowaru.longValue(),ilosc));
        }
        return towarWysylkaList;
    }
}
