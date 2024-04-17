package com.MagazynManagement.controller;

import com.MagazynManagement.dto.ProducentDto;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.service.TowarService;
import com.MagazynManagement.service.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ProducentController {

    @Autowired
    TowarService towarService;

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

    @GetMapping("/producent/towary-stan")
    public ModelAndView stanProduktow(Model model, Principal principal){
        List<Towar> list=towarService.getAllTowarByProducentId(principal.getName());
        return new ModelAndView("towary-stan","towar",list) ;
    }

    @GetMapping("/producent/edytuj-towar")
    public String edytujTowarForm(@RequestParam Long idTowaru, Model model){
        Towar towar=towarService.findById(idTowaru);
        model.addAttribute("towar",towar);
        return"edytuj-towar";
    }

    @PostMapping("/producent/edytuj-towar")
    public String edytujTowar(@ModelAttribute Towar towar){
        towarService.aktualizujTowar(towar);
        return "redirect:/producent/towary-stan";
    }
}
