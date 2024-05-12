package com.MagazynManagement.controller;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.MaterialRepozytory;
import com.MagazynManagement.repository.TowarRepository;
import com.MagazynManagement.repository.UzytkownikRepository;
import com.MagazynManagement.service.*;
//import com.MagazynManagement.service.ZamowienieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KoszykController {

    private final TowarService towarService;
    private final WysylkaService wysylkaService;
    private final UzytkownikRepository uzytkownikRepository;
    private final TowarWysylkaService towarWysylkaService;

    @PostMapping("/user/dodaj-do-koszyka")
    public String dodajDoKoszyka(@RequestParam Long idTowaru, @RequestParam Long idMagazynu, @RequestParam int ilosc, HttpSession session, HttpServletRequest request, Model model){
        List<PozycjaKoszyka> koszyk = (List<PozycjaKoszyka>) session.getAttribute("koszyk");
        StanMagazynowSesja stany = (StanMagazynowSesja) session.getAttribute("stany");
        if(koszyk == null){
            koszyk = new ArrayList<>();
            session.setAttribute("koszyk", koszyk);
        }

        Towar towar = towarService.findById(idTowaru);

        if(towar != null){
            PozycjaKoszyka istniejacaPozycja = znajdzPozycjeWKoszyku(towar, koszyk);
            if(istniejacaPozycja != null){
                istniejacaPozycja.setIlosc(istniejacaPozycja.getIlosc() + ilosc);
            } else {
                PozycjaKoszyka nowaPozycja = new PozycjaKoszyka(towar, ilosc);
                koszyk.add(nowaPozycja);
            }
        }

        stany.getStany().get(idMagazynu.intValue()-1).set(idTowaru.intValue()-1,stany.getStany().get(idMagazynu.intValue()-1).get(idTowaru.intValue()-1)+ilosc);
        //System.out.println(stany.getStany().get(idMagazynu.intValue()-1).get(idTowaru.intValue()-1));
        session.setAttribute("stany", stany);
        model.addAttribute("stany", stany);

        return "redirect:"+request.getHeader("Referer");
    }

    private PozycjaKoszyka znajdzPozycjeWKoszyku(Towar towar, List<PozycjaKoszyka> koszyk){
        for(PozycjaKoszyka pozycja : koszyk){
            if(pozycja.getTowar().getIdTowaru()==towar.getIdTowaru()){
                return pozycja;
            }
        }
        return null;
    }

    @GetMapping("/user/koszyk")
    public String wyswietlKoszyk(Model model, HttpSession session){
        List<PozycjaKoszyka> koszyk = (List<PozycjaKoszyka>) session.getAttribute("koszyk");
        if(koszyk == null){
            koszyk = new ArrayList<>();
            session.setAttribute("koszyk", koszyk);
        }

        model.addAttribute("koszyk", koszyk);
        //model.addAttribute("adresWysylki", "");
        return "koszyk";
    }

    @PostMapping("/user/zloz-zamowienie")
    public String zlozZamowienie(@RequestParam String adresWysylki,
                                 Model model,
                                 HttpSession session,
                                 Principal principal){
        List<PozycjaKoszyka> koszyk = (List<PozycjaKoszyka>) session.getAttribute("koszyk");
        StanMagazynowSesja stany = (StanMagazynowSesja) session.getAttribute("stany");

        if (koszyk == null || koszyk.isEmpty()){
            return "redirect:/user/koszyk";
        }

        wysylkaService.odejmijTowaryZeStanuMagazynowego(stany);

        Uzytkownik uzytkownik = uzytkownikRepository.findUserByEmail(principal.getName());
        Date data = new Date();
        String dataform = formatDate(data);

        if(uzytkownik.isCzyKlientHurtowy())
        {
            Wysylka wysylka=new Wysylka(null, uzytkownik.getIdUzytkownika(), null,null,"niezatwierdzona",0,dataform,adresWysylki);
            wysylkaService.dodajWysylke(wysylka);
        } else if (uzytkownik.isCzyKlientDetaliczny()) {
            Wysylka wysylka=new Wysylka(null,null, uzytkownik.getIdUzytkownika(), null,"niezatwierdzona",0,dataform,adresWysylki);
            wysylkaService.dodajWysylke(wysylka);
        }

        towarWysylkaService.dodajTowarWysylki(koszyk,wysylkaService.findPrzesylkeUzytkownika(uzytkownik.getIdUzytkownika(),dataform));




        koszyk.clear();
        stany.clear();
        session.setAttribute("koszyk", koszyk);
        session.setAttribute("stany", stany);
        return "redirect:/user/koszyk";
    }

    /*
    private float obliczKwoteZamowienia(List<PozycjaKoszyka> koszyk){
        float kwota = 0;
        for(PozycjaKoszyka pozycja : koszyk){
            kwota += pozycja.getMaterial().getCena() * pozycja.getIlosc();
        }
        return kwota;
    }
    */

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
