package com.MagazynManagement.service;

import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.dto.ProducentDto;
import com.MagazynManagement.dto.UserDto;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.repository.UzytkownikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                userDto.getNazwaFirmy(),
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

    public Uzytkownik findUserByEmail(String email){
        return uzytkownikRepository.findUserByEmail(email);
    }


    public List<Uzytkownik> getUserByRole(String role, String type, String currentAdminEmail) {
        List<Uzytkownik> users = uzytkownikRepository.findAll();

        return users.stream()
                .filter(user -> !user.getEmail().equals(currentAdminEmail))
                .filter(user -> {
                    switch (role){
                        case "client":
                            if("detaliczny".equalsIgnoreCase(type))
                                return user.isCzyKlientDetaliczny();
                            else if("hurtowy".equalsIgnoreCase(type))
                                return user.isCzyKlientHurtowy();
                            else
                                return false;
                        case "employee":
                            return user.isCzyPracownik();
                        case "producer":
                            return user.isCzyProducent();
                        default:
                            return false;
                    }
                })
                .collect(Collectors.toList());
    }


    public Uzytkownik getUserById(Long id){
        return uzytkownikRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public void updatePassword(Uzytkownik existingUser, String newPassword) {
        if(newPassword != null && !newPassword.isEmpty()){
            String encryptedPassword = passwordEncoder.encode(newPassword);
            existingUser.setHaslo(encryptedPassword);
        }
    }

    public void updateUser(Uzytkownik uzytkownik){
        uzytkownikRepository.save(uzytkownik);
    }

    public Uzytkownik getUzytkownikById(Long id){
        return uzytkownikRepository.findUzytkownikByID(id);
    }
}
