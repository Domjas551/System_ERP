package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Wysylka;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.WysylkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KoordynatorController {


    private final UzytkownikService uzytkownikService;
    private final WysylkaService wysylkaService;

    private final UserDetailsService userDetailsService;


    @GetMapping("/koordynator")
    public String pracownik(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Wysylka> l = wysylkaService.getWysylkaBezKierowcy();
        model.addAttribute("pracownik", userDetails);
        model.addAttribute("wysylki", l.size());
        return "koordynator-main";
    }
}
