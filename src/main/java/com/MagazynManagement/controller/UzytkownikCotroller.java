package com.MagazynManagement.controller;

import com.MagazynManagement.dto.KlientDto;
import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.dto.ProducentDto;
import com.MagazynManagement.dto.UserDto;
import com.MagazynManagement.entity.Klient;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.service.UzytkownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UzytkownikCotroller {


    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("userDto") UserDto userDto, Model model){
        if (!userDto.isCzyKlientDetaliczny() && !userDto.isCzyKlientHurtowy()) {
            // Obsługa błędu - żaden checkbox nie został zaznaczony
            model.addAttribute("error", "Musisz zaznaczyć przynajmniej jedną opcję");
            return "register";
        } else if (userDto.isCzyKlientDetaliczny() && userDto.isCzyKlientHurtowy()) {
            // Obsługa błędu - nie można wybrać jednocześnie klienta detalicznego i hurtowego
            model.addAttribute("error", "Nie można wybrać jednocześnie klienta detalicznego i hurtowego");
            return "register";
        }

        // Ustawienie wartości logicznych w zależności od wyboru użytkownika
        if (userDto.isCzyKlientDetaliczny()) {
            userDto.setCzyKlientHurtowy(false);
        } else if (userDto.isCzyKlientHurtowy()) {
            userDto.setCzyKlientDetaliczny(false);
        }

        Uzytkownik uzytkownik = uzytkownikService.saveUser(userDto);
        model.addAttribute("message", "Zostałeś zarejestrowany");
        return "register";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("klient", userDetails);
        return "user-main";
    }


    @GetMapping("/registration-producent")
    public String getProducentRegisterPage(Model model){
        model.addAttribute("producentDto", new ProducentDto());
        return "register-producent";
    }

    @PostMapping("/registration-producent")
    public String saveProducent(@ModelAttribute("producentDto") ProducentDto producentDto, Model model){
        Uzytkownik uzytkownik = uzytkownikService.saveProducent(producentDto);
        model.addAttribute("message", "Zostałeś zarejestrowany");
        return "register-producent";
    }
}
