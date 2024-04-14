package com.MagazynManagement.service;

import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.dto.ProducentDto;
import com.MagazynManagement.dto.UserDto;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.repository.UzytkownikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UzytkownikService {


    private final UzytkownikRepository uzytkownikRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Uzytkownik saveUser(UserDto userDto) {
        Uzytkownik uzytkownik = new Uzytkownik(userDto.getEmail(),
                userDto.getImie(),
                userDto.getNazwisko(),
                passwordEncoder.encode(userDto.getHaslo()),
                userDto.getTelefon(),
                null,
                null,
                null,
                true,
                false,
                false,
                userDto.isCzyKlientDetaliczny(),
                userDto.isCzyKlientHurtowy());

        return uzytkownikRepository.save(uzytkownik);
    }

    @Transactional
    public Uzytkownik savePracownik(PracownikDto pracownikDto){
        Uzytkownik uzytkownik = new Uzytkownik(pracownikDto.getEmail(),
                pracownikDto.getImie(),
                pracownikDto.getNazwisko(),
                passwordEncoder.encode(pracownikDto.getHaslo()),
                pracownikDto.getTelefon(),
                pracownikDto.getStanowisko(),
                null,
                pracownikDto.getPensja(),
                true,
                true,
                false,
                false,
                false);

        return uzytkownikRepository.save(uzytkownik);
    }

    public Uzytkownik saveProducent(ProducentDto producentDto) {
        Uzytkownik uzytkownik = new Uzytkownik(producentDto.getEmail(),
                null,
                null,
                passwordEncoder.encode(producentDto.getHaslo()),
                producentDto.getTelefon(),
                null,
                producentDto.getNazwaFirmy(),
                null,
                true,
                false,
                true,
                false,
                false);

        return uzytkownikRepository.save(uzytkownik);
    }
}
