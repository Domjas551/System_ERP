package com.MagazynManagement.service;

import com.MagazynManagement.controller.ErrorController;
import com.MagazynManagement.dto.TowarDto;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.repository.TowarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TowarService {

    ErrorController er=new ErrorController();

    @Autowired
    TowarRepository towarRepository;

    public List<Towar> getAllTowar(){return towarRepository.findAll();}
    public List<Towar> getAllTowarByProducentId(String email){
        return towarRepository.findAllByProducentId(email);
    }

    public Towar findById(Long idTowaru){
        return towarRepository.findByIdS(idTowaru);
    }

    @Transactional
    public String aktualizujTowar(Towar towar){
        Long idTowaru=towar.getIdTowaru();

        if(towarRepository.existsById(idTowaru)){
            towarRepository.saveS(towar.getIdTowaru(),towar.getNazwa(),towar.getKategoria());
            return "OK";
        }else{
            return "error";
        }
    }

    public Long getProducentId(String email){
        return towarRepository.findProducentId(email);
    }

    @Transactional
    public void saveTowar(TowarDto towarDto){
        //todo zabezpieczenie w wypadku błędu
        towarRepository.saveT(towarRepository.findProducentId(towarDto.getEmailProducenta()),towarDto.getNazwa(),towarDto.getKategoria());
    }

    public List<Towar> getTowaryByProducentAndMagazyn(Long idProducenta, Long idMagazynu){
        return towarRepository.findAllInMagazyn(idProducenta,idMagazynu);
    }

    public Towar findByIdInMagazyn(Long idTowaru, Long idMagazynu){
        return towarRepository.findByIdInMagazyn(idTowaru,idMagazynu);
    }

}
