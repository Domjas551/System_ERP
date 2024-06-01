package com.MagazynManagement.controller;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.service.ZadanieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
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
    private int edytowano = 0;

    @GetMapping("/manager/zadanie")
    public String getZadanie(Model model, Principal principal){
        List<Uzytkownik> listU = zadanieService.getMagazynier(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName())));
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
    public String wyswietlZadania(Model model, @PathVariable("id") Long id, Principal principal){
        List<Zadanie> listZ = zadanieService.getZadanieByManager(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("zadania", listZ);
        model.addAttribute("manager", id);
        model.addAttribute("count", listZ.size());
        model.addAttribute("userDetails", userDetails);
        return "zadania";
    }

    @GetMapping("/manager/zadanieDetails/{id}")
    public String zadanieDetails(Model model, @PathVariable("id") Long id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("edytowano", edytowano);
        model.addAttribute("zadanie", zadanieService.getZadanieById(id));
        model.addAttribute("pracownicy", zadanieService.getMagazynier(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName()))));
        model.addAttribute("manager", zadanieService.getKierownikId(principal.getName()));
        model.addAttribute("userDetails", userDetails);
        edytowano = 0;
        return "zadanieDetails";
    }

    @PostMapping("/manager/edytujZadanie")
    public String edytujZadanie(@ModelAttribute Zadanie zadanie){
        zadanieService.zapiszZadanie(zadanie);
        edytowano++;
        return "redirect:/manager/zadanieDetails/"+zadanie.getId_zadania();
    }

    @GetMapping("/magazynier/harmonogram/{id}")
    public String pokazHarmonogram(@PathVariable("id") Long id, Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Zadanie> zadanieList = zadanieService.getZadanieByPracownikId(id);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("count", zadanieList.size());
        model.addAttribute("idpracownika", id);
        model.addAttribute("zadania", zadanieList);
        return "harmonogram";
    }

    @GetMapping("/magazynier/zadanieDetailsPracownik/{id}")
    public String zadanieDetailsPracownik(Model model, @PathVariable("id") Long id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("zadanie", zadanieService.getZadanieById(id));
        model.addAttribute("idpracownika", zadanieService.getZadanieById(id).getId_pracownika());
        model.addAttribute("userDetails", userDetails);
        return "zadanieDetailsPracownik";
    }

    @PostMapping("/magazynier/wykonajZadanie")
    public String wykonajZadanie(@ModelAttribute Zadanie zadanie){
        zadanie.setStatus("wykonane");
        zadanieService.zapiszZadanie(zadanie);
        return "redirect:/magazynier/harmonogram/"+zadanie.getId_pracownika();
    }

    @GetMapping("/manager/doPrzydzialu")
    public String doPrzypisania(Model model, Principal principal){
        List<Zadanie> listZ = zadanieService.getDoPrzydzielenia(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName())));
        model.addAttribute("zadania", listZ);
        model.addAttribute("count", listZ.size());
        return "doPrzydzialu";
    }
}
