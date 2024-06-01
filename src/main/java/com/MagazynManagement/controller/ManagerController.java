package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Zadanie;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.ZadanieService;
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
public class ManagerController {


    private final UzytkownikService uzytkownikService;
    private final UserDetailsService userDetailsService;
    private final ZadanieService zadanieService;


    @GetMapping("/manager")
    public String manager(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Zadanie> listZ = zadanieService.getDoPrzydzielenia(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName())));
        model.addAttribute("nieprzydzielone", listZ.size());
        model.addAttribute("manager", userDetails);
        return "manager-main";
    }
}
