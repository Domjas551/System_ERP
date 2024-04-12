package com.MagazynManagement.service;

import com.MagazynManagement.entity.CustomUserDetails;
import com.MagazynManagement.entity.Konto;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.repository.KontoRepository;
import com.MagazynManagement.repository.UzytkownikRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    /*@Autowired
    private KontoRepository kontoRepository;*/


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Uzytkownik uzytkownik = uzytkownikRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Użytkownik o podanym emailu nie istnieje."));
        /*Konto konto = kontoRepository.findByLogin(username);
        if (konto == null)
            throw new UsernameNotFoundException("Taki użytkownik nie istnieje");*/

        return new CustomUserDetails(uzytkownik);
    }
}
