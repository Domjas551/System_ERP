package com.MagazynManagement.service;

import com.MagazynManagement.entity.PozycjaKoszyka;
import com.MagazynManagement.entity.StanMagazynowSesja;
import com.MagazynManagement.entity.TowarMagazyn;
import com.MagazynManagement.repository.TowarMagazynRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TowarMagazynService {

    private final TowarMagazynRepository towarMagazynRepository;

    public List<TowarMagazyn> pobierzTowarMagazynDlaMagazynu(Long idMagazynu){
        List<Object[]> results = towarMagazynRepository.findByMagazyn_IdMagazynu(idMagazynu);
        List<TowarMagazyn> towarMagazynList = new ArrayList<>();
        for (Object[] result : results) {
            Integer idTowaru = (Integer) result[0];
            Integer idMagazyn = (Integer) result[1];
            String nazwa = (String) result[2];
            String producent = (String) result[3];
            String kategoria = (String) result[4];
            Integer ilosc = (Integer) result[5];
            towarMagazynList.add(new TowarMagazyn(idTowaru.longValue(), idMagazyn.longValue(), nazwa, producent, kategoria, ilosc));
        }
        return towarMagazynList;
    }

    public boolean sprawdzDostepnosc(StanMagazynowSesja stany, List<PozycjaKoszyka> koszyk)
    {
        Long idTowaru;
        for(int i=0; i<koszyk.size();i++)
        {
            idTowaru=koszyk.get(i).getTowar().getIdTowaru();
            for(int j=0; j<stany.getStany().size(); j++)
            {
                if(towarMagazynRepository.findIloscTowarMagazyn(Long.valueOf(j+1),idTowaru)<stany.getStany().get(j).get((int)(long)(idTowaru-1)))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
