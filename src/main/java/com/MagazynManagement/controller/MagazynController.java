package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Magazyn;
import com.MagazynManagement.entity.PozycjaKoszyka;
import com.MagazynManagement.entity.StanMagazynowSesja;
import com.MagazynManagement.entity.TowarMagazyn;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.TowarMagazynService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MagazynController {

    private final MagazynService magazynService;
    private final TowarMagazynService towarMagazynService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/placowki")
    public ModelAndView getAllPlacowki(HttpSession session){

        StanMagazynowSesja stany = (StanMagazynowSesja) session.getAttribute("stany");
        List<Magazyn> list = magazynService.getAllMagazyn();

        if(stany == null){
            stany = new StanMagazynowSesja(list.size());
            for(int i=0;i< list.size();i++)
            {
                List<TowarMagazyn> TowarMagazynList = towarMagazynService.pobierzTowarMagazynDlaMagazynu(Long.valueOf(i+1));
                stany.inicjujMagazyn(i, TowarMagazynList.size());
                session.setAttribute("stany", stany);
            }
            session.setAttribute("stany", stany);
        }
        return new ModelAndView("placowki", "magazyn", list);
    }
}
