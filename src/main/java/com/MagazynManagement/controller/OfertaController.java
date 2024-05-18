package com.MagazynManagement.controller;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.PozycjaOfertyService;
import com.MagazynManagement.service.TowarMagazynService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OfertaController {

    private final PozycjaOfertyService pozycjaOfertyService;

    @GetMapping("/oferta")
    public String pokazTowarMagazyn(Model model, HttpSession session){
        List<PozycjaOferty> pozycjaOfertyList = pozycjaOfertyService.pobierzOferte();
        model.addAttribute("oferta", pozycjaOfertyList);
        return "oferta";
    }

    @PostMapping("/przejdz-do-zakupow")
    public String przejdzDoZakupow()
    {
        return "redirect:/placowki";
    }
}
