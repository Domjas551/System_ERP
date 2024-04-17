package com.MagazynManagement.service;

import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.repository.TowarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TowarService {

    @Autowired
    TowarRepository towarRepository;

    public List<Towar> getAllTowar(){return towarRepository.findAll();}
    public List<Towar> getAllTowarByProducentId(String email){
        return towarRepository.findAllByProducentId(email);
    }

    public Towar findById(Long idTowaru){
        return towarRepository.findByIdS(idTowaru)
                .orElseThrow(() -> new EntityNotFoundException("Towar nie znaleziony"));
    }

    @Transactional
    public void aktualizujTowar(Towar towar){
        Long idTowaru=towar.getIdTowaru();

        if(towarRepository.existsById(idTowaru)){
            towarRepository.saveS(towar.getIdTowaru(),towar.getNazwa(),towar.getKategoria());
        }else{
            throw new EntityNotFoundException("Towar nie znaleziony");
        }
    }

}
