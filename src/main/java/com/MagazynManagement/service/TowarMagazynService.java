package com.MagazynManagement.service;

import com.MagazynManagement.entity.TowarMagazyn;
import com.MagazynManagement.repository.TowarMagazynProjection;
import com.MagazynManagement.repository.TowarMagazynRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TowarMagazynService {

    private final TowarMagazynRepository towarMagazynRepository;

    public List<TowarMagazynProjection> pobierzTowarMagazynDlaMagazynu(Long idMagazynu){
        return towarMagazynRepository.findByMagazyn_IdMagazynu(idMagazynu);
    }

    public void saveStanMagazynu(TowarMagazyn towarMagazyn){towarMagazynRepository.save(towarMagazyn);
    }
}
