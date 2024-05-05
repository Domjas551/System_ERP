package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Magazyn;
import com.MagazynManagement.entity.StanMagazynowSesja;
import com.MagazynManagement.entity.TowarMagazyn;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.TowarMagazynService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TowarMagazynController {

    private final TowarMagazynService towarMagazynService;
    private final MagazynService magazynService;

    @GetMapping("/towar-magazyn/{id}")
    public String pokazTowarMagazyn(@PathVariable("id") Long id, Model model, HttpSession session){
        List<TowarMagazyn> TowarMagazynList = towarMagazynService.pobierzTowarMagazynDlaMagazynu(id);
        StanMagazynowSesja stany = (StanMagazynowSesja) session.getAttribute("stany");
        model.addAttribute("towarMagazyn", TowarMagazynList);
        Magazyn magazyn = magazynService.getMagazyn(id);
        if(magazyn != null)
            model.addAttribute("adresMagazynu", magazyn.getAdres());
        if(stany.getStany().get(id.intValue()-1).isEmpty()){
            stany.inicjujMagazyn(id.intValue()-1, TowarMagazynList.size());
            session.setAttribute("stany", stany);
        }
        System.out.println(stany);
        model.addAttribute("stany", stany);
        return "towar-magazyn";
    }
}
