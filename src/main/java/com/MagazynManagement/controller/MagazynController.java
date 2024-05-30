package com.MagazynManagement.controller;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.dto.*;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.TowarMagazynService;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.ZadanieService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MagazynController {

    private final MagazynService magazynService;
    private final TowarMagazynService towarMagazynService;
    private final ZadanieService zadanieService;
    private final UzytkownikService uzytkownikService;

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

    @GetMapping("/manager/towaryManager")
    public String iloscTowarow(Model model, Principal principal){
        List<TowarMagazynDto> listDto = new ArrayList<>();
        List<TowarMagazyn> listTM = towarMagazynService.pobierzTowarMagazynDlaMagazynu(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName())));
        for (TowarMagazyn t: listTM){
            TowarMagazynDto dto = new TowarMagazynDto();
            dto.setId(t.getIdTowaru());
            dto.setNazwa_firmy(t.getProducent());
            dto.setIlosc(t.getIlosc());
            dto.setMax_ilosc(towarMagazynService.getMaxIlosc(t.getIdMagazynu(), t.getIdTowaru()));
            dto.setKategoria(t.getKategoria());
            dto.setNazwa(t.getNazwa());
            listDto.add(dto);
        }
        model.addAttribute("towary", listDto);
        return "towaryManager";
    }
}
