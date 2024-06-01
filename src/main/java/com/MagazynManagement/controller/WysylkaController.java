package com.MagazynManagement.controller;

import com.MagazynManagement.dto.WysylkaDto;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.entity.Wysylka;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.WysylkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WysylkaController {

    int przypisano = 0;

    private final UzytkownikService uzytkownikService;
    private final WysylkaService wysylkaService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/koordynator/wysylkiPending")
    public String wysylki(Model model, Principal principal){
        List<Wysylka> listW = wysylkaService.getWysylkaBezKierowcy();
        List<WysylkaDto> listDto = new ArrayList<>();
        for (Wysylka w : listW){
            WysylkaDto dto = new WysylkaDto();
            dto.setId_wysylki(w.getId_wysylki());
            dto.setData(w.getData());
            dto.setAdres(w.getAdres());
            dto.setStatus(w.getStatus());
            if (w.getId_klienta_detalicznego() != null){
                dto.setHurtowy(0);
                dto.setImie_nazwisko(uzytkownikService.getUzytkownikById(w.getId_klienta_detalicznego()).getImie() + " " + uzytkownikService.getUzytkownikById(w.getId_klienta_detalicznego()).getNazwisko());
            }
            else{
                dto.setHurtowy(1);
                dto.setNazwa_firmy(uzytkownikService.getUzytkownikById(w.getId_klienta_hurtowego()).getNazwaFirmy());
            }
            listDto.add(dto);
        }
        model.addAttribute("wysylki", listDto);
        return "wysylkiPending";
    }

    @GetMapping("/koordynator/wysylkaDetails/{id}")
    public String wysylkaDetails(Model model, @PathVariable("id") Long id, Principal principal){
        WysylkaDto dto = new WysylkaDto();
        Wysylka w = wysylkaService.getById(id);
        dto.setId_wysylki(w.getId_wysylki());
        dto.setStatus(w.getStatus());
        dto.setData(w.getData());
        dto.setAdres(w.getAdres());
        dto.setId_kierowcy(w.getId_kierowcy());
        if (w.getId_klienta_detalicznego() != null){
            dto.setHurtowy(0);
            dto.setImie_nazwisko(uzytkownikService.getUzytkownikById(w.getId_klienta_detalicznego()).getImie() + " " + uzytkownikService.getUzytkownikById(w.getId_klienta_detalicznego()).getNazwisko());
        }
        else{
            dto.setHurtowy(1);
            dto.setNazwa_firmy(uzytkownikService.getUzytkownikById(w.getId_klienta_hurtowego()).getNazwaFirmy());
        }
        List<Uzytkownik> listU = wysylkaService.getKierowcy();
        model.addAttribute("wysylka", dto);
        model.addAttribute("pracownicy", listU);
        model.addAttribute("przypisano", przypisano);
        przypisano = 0;
        return "wysylkaDetails";
    }

    @PostMapping("/koordynator/przypisz")
    public String przypisz(@ModelAttribute Wysylka wysylka){
        przypisano = 1;
        Wysylka w = wysylkaService.getById(wysylka.getId_wysylki());
        w.setId_kierowcy(wysylka.getId_kierowcy());
        w.setStatus("niezrealizowana");
        wysylkaService.update(w);
        return "redirect:/koordynator/wysylkaDetails/"+wysylka.getId_wysylki();
    }
}
