package com.MagazynManagement.controller;

import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.entity.Pracownik;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.service.UzytkownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AdministratorController {


    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;


    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("admin", userDetails);
        return "admin-main";
    }

    @GetMapping("/admin/add-pracownik")
    public String addPracownikPage(Model model){
        model.addAttribute("pracownikDto", new PracownikDto());
        return "add-pracownik";
    }

    @PostMapping("/admin/add-pracownik")
    public String savePracownik(@ModelAttribute("pracownikDto") PracownikDto pracownikDto, Model model){
        Uzytkownik savedUzytkownik = uzytkownikService.savePracownik(pracownikDto);
        model.addAttribute("message", "Pracownik dodany");
        return "add-pracownik";
    }
}
