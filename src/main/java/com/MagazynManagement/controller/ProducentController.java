package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Produkt;
import com.MagazynManagement.service.ProduktService;
import com.MagazynManagement.service.UzytkownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ProducentController {

    @Autowired
    ProduktService produktService;

    @Autowired
    UzytkownikService uzytkownikService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/producent")
    public String producent(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("producent", userDetails);
        return "producent-main";
    }

    @GetMapping("/producent/produkty-stan")
    public ModelAndView stanProduktow(){
        List<Produkt> list=produktService.getAllProducts();
        return new ModelAndView("produkty-stan","produkt",list) ;
    }
}
