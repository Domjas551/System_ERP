package com.MagazynManagement.controller;

import com.MagazynManagement.dto.DostawaDto;
import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.DostawaRepository;
import com.MagazynManagement.service.DostawaService;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.ZadanieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ZadanieController {

    private final ZadanieService zadanieService;
    private final UserDetailsService userDetailsService;
    private final DostawaService dostawaService;
    private final MagazynService magazynService;
    private final UzytkownikService uzytkownikService;
    private final DostawaRepository dostawaRepository;

    private int dodano = 0;
    private int edytowano = 0;
    private int przypisano = 0;

    @GetMapping("/manager/zadanie")
    public String getZadanie(Model model, Principal principal){
        List<Uzytkownik> listU = zadanieService.getMagazynier(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName())));
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("dodano", dodano);
        model.addAttribute("manager", userDetails);
        model.addAttribute("pracownicy", listU);
        dodano = 0;
        return "zadanie";
    }

    @PostMapping("/manager/dodajZadanie")
    public String dodajZadanie(@ModelAttribute Zadanie zadanie){
        zadanieService.dodajZadanie(zadanie);
        dodano++;
        return "redirect:/manager/zadanie";
    }

    @GetMapping("/manager/zadania/{id}")
    public String wyswietlZadania(Model model, @PathVariable("id") Long id, Principal principal){
        List<Zadanie> listZ = zadanieService.getZadanieByManager(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("zadania", listZ);
        model.addAttribute("manager", id);
        model.addAttribute("count", listZ.size());
        model.addAttribute("userDetails", userDetails);
        return "zadania";
    }

    @GetMapping("/manager/zadanieDetails/{id}")
    public String zadanieDetails(Model model, @PathVariable("id") Long id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("edytowano", edytowano);
        model.addAttribute("zadanie", zadanieService.getZadanieById(id));
        model.addAttribute("pracownicy", zadanieService.getMagazynier(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName()))));
        model.addAttribute("manager", zadanieService.getKierownikId(principal.getName()));
        model.addAttribute("userDetails", userDetails);
        edytowano = 0;
        return "zadanieDetails";
    }

    @PostMapping("/manager/edytujZadanie")
    public String edytujZadanie(@ModelAttribute Zadanie zadanie){
        zadanieService.zapiszZadanie(zadanie);
        edytowano++;
        return "redirect:/manager/zadanieDetails/"+zadanie.getId_zadania();
    }

    @GetMapping("/magazynier/harmonogram/{id}")
    public String pokazHarmonogram(@PathVariable("id") Long id, Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Zadanie> zadanieList = zadanieService.getZadanieByPracownikId(id);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("count", zadanieList.size());
        model.addAttribute("idpracownika", id);
        model.addAttribute("zadania", zadanieList);
        return "harmonogram";
    }

    @GetMapping("/magazynier/zadanieDetailsPracownik/{id}")
    public String zadanieDetailsPracownik(Model model, @PathVariable("id") Long id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("zadanie", zadanieService.getZadanieById(id));
        model.addAttribute("idpracownika", zadanieService.getZadanieById(id).getId_pracownika());
        model.addAttribute("userDetails", userDetails);
        return "zadanieDetailsPracownik";
    }

    @PostMapping("/magazynier/wykonajZadanie")
    public String wykonajZadanie(@ModelAttribute Zadanie zadanie){
        zadanie.setStatus("wykonane");
        zadanieService.zapiszZadanie(zadanie);
        return "redirect:/magazynier/harmonogram/"+zadanie.getId_pracownika();
    }

    @GetMapping("/manager/doPrzydzialu")
    public String doPrzypisania(Model model, Principal principal){
        List<Zadanie> listZ = zadanieService.getDoPrzydzielenia(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName())));
        model.addAttribute("zadania", listZ);
        model.addAttribute("count", listZ.size());
        return "doPrzydzialu";
    }

    @GetMapping("/manager/dostawaBezKierowcy")
    public String dostawyBezKierowcy(Model model, Principal principal) throws Exception {
        Long id = zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName()));
        List<DostawaDto> listDto = new ArrayList<>();
        List<Long> listD = dostawaService.findDostawaBezKierowcy(id);
        for (Long d : listD){
            DostawaDto dto = new DostawaDto();
            dto.setId_dostawy(d);
            dto.setId_producenta(dostawaRepository.findDostawaProducent(d));
            dto.setData(dostawaRepository.findDostawaData(d));
            dto.setAdres(dostawaRepository.findDostawaAdres(d));
            dto.setAdres_magazynu(magazynService.getAdres(id));
            dto.setNazwa_firmy(uzytkownikService.getUzytkownikById(dto.getId_producenta()).getNazwaFirmy());
            listDto.add(dto);
        }
        model.addAttribute("dostawy", listDto);
        return "dostawaBezKierowcy";
    }

    @GetMapping("/manager/dostawaDetails/{id}")
    public String dostawaDetails(Model model, @PathVariable("id") Long id, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        DostawaDto dto = new DostawaDto();
        dto.setId_dostawy(id);
        dto.setNazwa_firmy(uzytkownikService.getUzytkownikById(dostawaRepository.findDostawaProducent(id)).getNazwaFirmy());
        dto.setAdres(dostawaRepository.findDostawaAdres(id));
        dto.setData(dostawaRepository.findDostawaData(id));
        dto.setId_kierowcy(dostawaRepository.findDostawaKierowca(id));
        model.addAttribute("dostawa", dto);
        model.addAttribute("pracownicy", zadanieService.getMagazynier(zadanieService.getMagazynByKierownik(zadanieService.getKierownikId(principal.getName()))));
        model.addAttribute("manager", zadanieService.getKierownikId(principal.getName()));
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("przypisano", przypisano);
        przypisano = 0;
        return "dostawaDetails";
    }

    @PostMapping("/manager/dodajKierowce")
    public String dodajKierowce(@ModelAttribute DostawaDto dto){
        dostawaRepository.dodajKierowce(dto.getId_kierowcy(), dto.getId_dostawy());
        przypisano++;
        return "redirect:/manager/dostawaDetails/"+dto.getId_dostawy();
    }
}
