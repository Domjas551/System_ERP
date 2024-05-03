package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Towar;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ErrorController {

    //funkcja do wyświetlenia strony z errorami
    public String error( String wiadomosc, String adresPowrotny, Model model){
        model.addAttribute("wiadomosc",wiadomosc);
        model.addAttribute("adres",adresPowrotny);
        return "errorPage";
    }
}
