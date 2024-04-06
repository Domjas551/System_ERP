package com.MagazynManagement.service;

import com.MagazynManagement.entity.Produkt;
import com.MagazynManagement.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduktService {

    @Autowired
    ProduktRepository produktRepository;

    public List<Produkt> getAllProducts(){return produktRepository.findAll();}

}
