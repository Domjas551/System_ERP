package com.MagazynManagement.service;

import com.MagazynManagement.dto.TowarMagazynDto;
import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.TowarMagazynRepository;
import com.MagazynManagement.repository.TowarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TowarMagazynService {

    private final TowarMagazynRepository towarMagazynRepository;
    private final KomunikatService komunikatService;

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

    public boolean sprawdzDostepnosc(StanMagazynowSesja stany, List<PozycjaKoszyka> koszyk) throws Exception {
        for(int i=0; i<koszyk.size();i++)
        {
            Towar towar=koszyk.get(i).getTowar();
            for(int j=0; j<stany.getStany().size(); j++)
            {
                if(towarMagazynRepository.findIloscTowarMagazyn(Long.valueOf(j+1),towar.getIdTowaru())<stany.getStany().get(j).get((int)(long)(towar.getIdTowaru()-1))+20) {
                    komunikatService.addKomunikat(null, towar.getIdProducenta(),"Zabrakło towaru "+towar.getNazwa()+" w magazynie "+(j+1)+".",0,formatDate(new Date()));
                    if (towarMagazynRepository.findIloscTowarMagazyn(Long.valueOf(j + 1), towar.getIdTowaru()) < stany.getStany().get(j).get((int) (long) (towar.getIdTowaru() - 1))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public int getMaxIlosc(Long id_m, Long id_p){
        return towarMagazynRepository.getMaxIlosc(id_m, id_p);
    }

    public void zmienMaxIlosc(TowarMagazynDto tm){
        if (tm.getMax_ilosc() > 999) tm.setMax_ilosc(999);
        if (tm.getMax_ilosc() < 0) tm.setMax_ilosc(0);
        towarMagazynRepository.zamienMaxIlosc(tm.getId_magazynu(), tm.getId(), tm.getMax_ilosc());
    }
}
