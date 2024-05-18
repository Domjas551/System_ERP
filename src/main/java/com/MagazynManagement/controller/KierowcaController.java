package com.MagazynManagement.controller;

import com.MagazynManagement.entity.Dostawa;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.service.DostawaService;
import com.MagazynManagement.service.UzytkownikService;
import lombok.RequiredArgsConstructor;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KierowcaController {

    @Autowired
    DostawaService dostawaService;

    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;


    //funkcja do ładowania głównej strony kierowców
    @GetMapping("/kierowca")
    public String pracownik(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("pracownik", userDetails);
        return "kierowca-main";
    }

    //funkcja do ładowania strony zadania-aktywne
    @GetMapping("/kierowca/zadania-aktywne")
    public ModelAndView zadania_aktywneGet(Model model, Principal principal){

        List<Dostawa> list=new ArrayList<>();

        try{
            list=dostawaService.findAllActiveDostawaByKierowcaId(dostawaService.getKierowcaId(principal.getName()));

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                list.add(t);
            }else if(list.get(0)==null){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                list.set(0,t);
            }


            //obsługa błędu bazy danych
        }catch(Exception e){
            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                list.add(t);
            }else if(list.get(0)==null){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                list.set(0,t);
            }
        }

        return new ModelAndView("zadania-aktywne","zadania",list) ;
    }

    //funkcja do ładowania strony zadania-aktywne
    @GetMapping("/kierowca/zadania-nieaktywne")
    public ModelAndView zadania_nieaktywneGet(Model model, Principal principal){

        List<Dostawa> list=new ArrayList<>();

        try{
            list=dostawaService.findAllNotActiveDostawaByKierowcaId(dostawaService.getKierowcaId(principal.getName()));

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                list.add(t);
            }else if(list.get(0)==null){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                list.set(0,t);
            }


            //obsługa błędu bazy danych
        }catch(Exception e){
            e.printStackTrace();
            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                list.add(t);
            }else if(list.get(0)==null){
                Dostawa t=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                list.set(0,t);
            }
        }

        return new ModelAndView("zadania-nieaktywne","zadania",list) ;
    }

    //funkcja do ładowania strony zadanie-aktywne
    @GetMapping("/kierowca/zadanie-aktywne")
    public String zadanie_aktywneGet(@RequestParam Long idDostawy, @RequestParam String typ,
                                           @RequestParam String message, Model model, Principal principal){

        model.addAttribute("message",message);

        if(typ.equals("wysylka")){
            try{
                Dostawa d=dostawaService.findWysylkaById(idDostawy);

                if(d==null){
                    d=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                    model.addAttribute("zadanie",d);
                }else{
                    model.addAttribute("zadanie",d);
                }


                //obsługa błędu bazy danych
            }catch (Exception e){
                Dostawa d=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                model.addAttribute("zadanie",d);
            }

        }else if(typ.equals("dostawa")){
            try{
                Dostawa d=dostawaService.findDostawaById(idDostawy);

                if(d==null){
                    d=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                    model.addAttribute("zadanie",d);
                }else{
                    model.addAttribute("zadanie",d);
                }


                //obsługa błędu bazy danych
            }catch (Exception e){
                Dostawa d=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                model.addAttribute("zadanie",d);
            }
        }
        return "zadanie-aktywne";
    }

    //strona do przyjmowania post ze strony zadanie-aktywne
    @PostMapping("/kierowca/zadanie-aktywne")
    public String edytujTowar(@ModelAttribute Dostawa zadanie, Model model){

        try{
            if(zadanie.getTyp().equals("wysylka")){
                dostawaService.aktualizujStatusWysylka(zadanie.getIdDostawy(), zadanie.getStatus());
            }else if(zadanie.getTyp().equals("dostawa")){
                dostawaService.aktualizujStatusDostawy(zadanie.getIdDostawy(), zadanie.getStatus());
            }


            //obsługa błędu bazy danych
        }catch(Exception e){
            return "redirect:zadanie-aktywne?idDostawy="+zadanie.getIdDostawy()+"&typ="
                    +zadanie.getTyp()+"&message='Error: aktualizacja statusu nieudana'";
        }

        return "redirect:zadanie-nieaktywne?idDostawy="+zadanie.getIdDostawy()+"&typ="+zadanie.getTyp();
    }

    //funkcja do ładowania strony zadanie-nieaktywne
    @GetMapping("/kierowca/zadanie-nieaktywne")
    public String zadanie_nieaktywneGet(@RequestParam Long idDostawy, @RequestParam String typ,
                                      Model model, Principal principal){

        if(typ.equals("wysylka")){
            try{
                Dostawa d=dostawaService.findNieaktywnaWysylkaById(idDostawy);

                if(d==null){
                    d=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                    model.addAttribute("zadanie",d);
                }else{
                    model.addAttribute("zadanie",d);
                }

                //obsługa błędu bazy danych
            }catch (Exception e){
                Dostawa d=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                model.addAttribute("zadanie",d);
            }

        }else if(typ.equals("dostawa")){
            try{
                Dostawa d=dostawaService.findNieaktywnaDostawaById(idDostawy);

                if(d==null){
                    d=new Dostawa(Integer.toUnsignedLong(1),"brakWartosci","","");
                    model.addAttribute("zadanie",d);
                }else{
                    model.addAttribute("zadanie",d);
                }


                //obsługa błędu bazy danych
            }catch (Exception e){
                Dostawa d=new Dostawa(Integer.toUnsignedLong(1),"bladPob","","");
                model.addAttribute("zadanie",d);
            }
        }
        return "zadanie-nieaktywne";
    }

}
