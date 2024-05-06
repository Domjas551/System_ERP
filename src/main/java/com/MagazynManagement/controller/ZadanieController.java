package com.MagazynManagement.controller;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.service.ZadanieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ZadanieController {

    private final ZadanieService zadanieService;
    private final UserDetailsService userDetailsService;

    private int dodano = 0;
    private int anulowano = 0;

    @GetMapping("/manager/zadanie")
    public String getZadanie(Model model, Principal principal){
        List<Uzytkownik> listU = zadanieService.getMagazynier();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("dodano", dodano);
        model.addAttribute("manager", userDetails);
        model.addAttribute("pracownicy", listU);
        dodano = 0;
        return "zadanie";
    }

    @PostMapping("/manager/dodajZadanie")
    public String dodajZadanie(@ModelAttribute Zadanie zadanie){
        zadanieService.dodajZadanie(zadanie);
        dodano++;
        return "redirect:/manager/zadanie";
    }

    @GetMapping("/manager/zadania/{id}")
    public String wyswietlZadania(Model model, @PathVariable("id") Long id){
        List<Zadanie> listZ = zadanieService.getZadanieByManager(id);
        model.addAttribute("zadania", listZ);
        model.addAttribute("anulowano", anulowano);
        model.addAttribute("manager", id);
        anulowano=0;
        return "zadania";
    }

    @PostMapping("/manager/anulujZadanie/{id}")
    public String anulujZadanie(@ModelAttribute Zadanie zadanie, @PathVariable("id") Long id){
        System.out.println(zadanie.getId_zadania());
        zadanie.setId_kierownika(id);
        zadanieService.zapiszZadanie(zadanie);
        anulowano++;
        return "redirect:/manager/zadania";
    }

    /*
    @PostMapping("/pracownik/harmonogram/wykonane/{id}")
    public String wykonajZadanie(@ModelAttribute Zadanie zadanie, @PathVariable("id") Long idpracownika){
        zadanieService.wykonajZadanie(zadanie.getId_zadania(), zadanie.getStatus());
        return "redirect:/pracownik/harmonogram/"+idpracownika;
    }*/

    @GetMapping("/pracownik/harmonogram/{id}")
    public String pokazStanMagazynu(@PathVariable("id") Long id, Model model){
        List<Zadanie> zadanieList = zadanieService.getZadanieByPracownikId(id);
        model.addAttribute("idpracownika", id);
        model.addAttribute("harmonogram", zadanieList);
        return "harmonogram";
    }
}