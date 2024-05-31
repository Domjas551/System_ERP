package com.MagazynManagement.service;

import com.MagazynManagement.entity.Komunikat;
import com.MagazynManagement.repository.KomunikatRepository;
import com.MagazynManagement.repository.TowarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KomunikatService {

    @Autowired
    KomunikatRepository komunikatRepository;

    @Autowired
    TowarRepository towarRepository;

    public List<Komunikat> getAllUnreadKomunikat(String email) throws Exception{
        return komunikatRepository.findAllUnreadKomunikat(towarRepository.findProducentId(email));
    }

    public List<Komunikat> getAllReadKomunikat(String email) throws Exception{
        return komunikatRepository.findAllReadKomunikat(towarRepository.findProducentId(email));
    }

    public Komunikat findKomunikat(Long idKomunikatu) throws Exception{
        return komunikatRepository.findKomunikat(idKomunikatu);
    }

    @Transactional
    public void zaznaczJakoPrzeczytana(Long idKomunikatu) throws Exception{
        komunikatRepository.zmienNaPrzeczytano(idKomunikatu);
    }
}
