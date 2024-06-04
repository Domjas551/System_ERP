package com.MagazynManagement.service;

import com.MagazynManagement.dto.KlientDto;
import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.entity.Klient;
import com.MagazynManagement.entity.Konto;
import com.MagazynManagement.entity.Pracownik;
import com.MagazynManagement.repository.KontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KontoService {

    private final KontoRepository kontoRepository;

    private final PasswordEncoder passwordEncoder;

    private final String rola = "USER";

    public List<Konto> getAllKonto(){
        return kontoRepository.findAll();
    }
}
