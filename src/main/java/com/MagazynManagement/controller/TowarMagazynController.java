package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Magazyn;
import com.MagazynManagement.entity.TowarMagazyn;
import com.MagazynManagement.repository.TowarMagazynProjection;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.TowarMagazynService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TowarMagazynController {

    private final TowarMagazynService towarMagazynService;
    private final MagazynService magazynService;

    @GetMapping("/towar-magazyn/{id}")
    public String pokazTowarMagazyn(@PathVariable("id") Long id, Model model){
        List<TowarMagazynProjection> TowarMagazynList = towarMagazynService.pobierzTowarMagazynDlaMagazynu(id);
        System.out.println(TowarMagazynList);
        model.addAttribute("towarMagazyn", TowarMagazynList);
        Magazyn magazyn = magazynService.getMagazyn(id);
        if(magazyn != null)
            model.addAttribute("adresMagazynu", magazyn.getAdres());
        return "towar-magazyn";
    }
}
