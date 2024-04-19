package com.MagazynManagement.service;

import com.MagazynManagement.controller.ErrorController;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.repository.TowarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

}
