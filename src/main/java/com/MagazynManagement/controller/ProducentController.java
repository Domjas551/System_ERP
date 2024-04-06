package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Produkt;
import com.MagazynManagement.service.ProduktService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/producent")
    public String producent(){
        return "producent-main";
    }

    @GetMapping("/producent/produkty-stan")
    public ModelAndView stanProduktow(){
        List<Produkt> list=produktService.getAllProducts();
        return new ModelAndView("produkty-stan","produkt",list) ;
    }
}
