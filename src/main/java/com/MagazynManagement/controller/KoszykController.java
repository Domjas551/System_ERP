package com.MagazynManagement.controller;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.UzytkownikRepository;
import com.MagazynManagement.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final TowarMagazynService towarMagazynService;
    private final ZadanieService zadanieService;

    @PostMapping("/user/dodaj-do-koszyka")
    public String dodajDoKoszyka(@RequestParam Long idTowaru, @RequestParam Long idMagazynu, @RequestParam int ilosc, HttpSession session, HttpServletRequest request, Model model){
        if(ilosc==0)
        {
            return "redirect:"+request.getHeader("Referer");
        }
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

        if(!towarMagazynService.sprawdzDostepnosc(stany, koszyk))
        {
            koszyk.clear();
            stany.clear();
            session.setAttribute("koszyk", koszyk);
            session.setAttribute("stany", stany);
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

        String opis="";
        boolean s=false;
        for(int i=0; i<stany.getStany().size(); i++)
        {
            for(int j=0; j<stany.getStany().get(i).size();j++)
            {
                if(stany.getStany().get(i).get(j)!=0)
                {
                    s=true;
                    break;
                }
            }
            if(s)
            {
                opis=opis+("Magazyn "+(i+1)+": Przygotuj towary do wysylki o id "+wysylkaService.findPrzesylkeUzytkownika(uzytkownik.getIdUzytkownika(),dataform)+".\n" +
                        "Towary do wysylki:");

                for(int j=0; j<stany.getStany().get(i).size();j++)
                {
                    if(stany.getStany().get(i).get(j)!=0)
                    {
                        opis=opis+("\n - id towaru: "+(j+1)+", ilosc: "+stany.getStany().get(i).get(j)+" ton.");
                    }
                }
                System.out.println(opis);
                Zadanie zadanie=new Zadanie(null,null,null,opis,"do przydzialu");
                zadanieService.zapiszZadanie(zadanie);
                opis="";
                s=false;
            }
        }

        koszyk.clear();
        stany.clear();
        session.setAttribute("koszyk", koszyk);
        session.setAttribute("stany", stany);
        return "redirect:/user/koszyk";
    }

    @PostMapping("/user/oproznij-koszyk")
    public String oproznijKoszyk(HttpSession session)
    {
        List<PozycjaKoszyka> koszyk = (List<PozycjaKoszyka>) session.getAttribute("koszyk");
        StanMagazynowSesja stany = (StanMagazynowSesja) session.getAttribute("stany");

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
