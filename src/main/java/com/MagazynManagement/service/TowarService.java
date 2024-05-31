package com.MagazynManagement.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.MagazynManagement.dto.TowarDto;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.repository.TowarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TowarService {

    @Autowired
    TowarRepository towarRepository;

    public List<Towar> getAllTowar(){return towarRepository.findAll();}
    public List<Towar> getAllTowarByProducentId(String email) throws Exception{
        return towarRepository.findAllByProducentId(email);
    }

    public Towar findById(Long idTowaru) throws Exception {
        return towarRepository.findByIdS(idTowaru);
    }

    @Transactional
    public void aktualizujTowar(Towar towar) throws Exception{
        Long idTowaru=towar.getIdTowaru();

        if(towarRepository.existsById(idTowaru)){
            towarRepository.saveS(towar.getIdTowaru(),towar.getNazwa(),towar.getKategoria());
        }else{
            throw new Exception("Nie znaleziono towaru");
        }
    }

    public Long getProducentId(String email){
        return towarRepository.findProducentId(email);
    }

    @Transactional
    public void saveTowar(TowarDto towarDto) throws Exception{
        towarRepository.saveT(towarRepository.findProducentId(towarDto.getEmailProducenta()),towarDto.getNazwa(),towarDto.getKategoria());
    }

    public List<Towar> getTowaryByProducentAndMagazyn(Long idProducenta, Long idMagazynu) throws Exception{
        return towarRepository.findAllInMagazyn(idProducenta,idMagazynu);
    }

    public Towar findByIdInMagazyn(Long idTowaru, Long idMagazynu) throws Exception{
        return towarRepository.findByIdInMagazyn(idTowaru,idMagazynu);
    }

    public Towar getTowarById(Long id)
    {
        return towarRepository.getTowarById(id);
    }

    @Transactional
    public void saveWysylka(Long idTowaru, Long idMagazynu,Long idProducenta,String adres, Long ilosc) throws Exception{

        LocalDateTime d = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

        String data = d.format(dateFormatter);

        towarRepository.saveWysylka(idProducenta, idMagazynu, data, adres);

        Long idWysylki=towarRepository.getLastProducentWysylkaId(idProducenta);

        towarRepository.saveWysylkaTowar(idTowaru,idWysylki,ilosc);

        towarRepository.zaktualizujStanTowaruMagazynu(idTowaru, idMagazynu, ilosc);

    }

}
