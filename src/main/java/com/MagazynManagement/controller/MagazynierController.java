package com.MagazynManagement.controller;

import com.MagazynManagement.service.UzytkownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MagazynierController {


    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;


    @GetMapping("/magazynier")
    public String pracownik(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("pracownik", userDetails);
        return "magazynier-main";
    }
}
