package com.MagazynManagement.controller;

import com.MagazynManagement.dto.TowarDostawaDto;
import com.MagazynManagement.dto.TowarDto;
import com.MagazynManagement.entity.Dostawa;
import com.MagazynManagement.entity.Komunikat;
import com.MagazynManagement.entity.Magazyn;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.service.KomunikatService;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.TowarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProducentController {

    @Autowired
    TowarService towarService;

    @Autowired
    MagazynService magazynService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    KomunikatService komunikatService;

    //funkcja do ładowania strony producenta
    @GetMapping("/producent")
    public String producent(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("producent", userDetails);
        return "producent-main";
    }

    //funkcja do ładowania strony stanów towarów
    @GetMapping("/producent/towary-stan")
    public ModelAndView stanProduktow(Model model, Principal principal){

        List<Towar> list=new ArrayList<> ();

        try{
            list=towarService.getAllTowarByProducentId(principal.getName());

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        "brakTowarow","k",0,0);
                list.add(t);
            }else if(list.get(0)==null){
                Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        "brakTowarow","k",0,0);
                list.set(0,t);
            }

            //obsługa błędu bazy danych
        }catch (Exception e){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "bladPob","k",0,0);
            list.add(t);
        }

        return new ModelAndView("towary-stan","towar",list) ;

    }

    //funkcja do wyświetlania strony edycji towarów
    @GetMapping("/producent/edytuj-towar")
    public String edytujTowarForm(@RequestParam Long idTowaru, @RequestParam String message, Model model){

        model.addAttribute("message",message);

        try{
            Towar towar=towarService.findById(idTowaru);

            if(towar==null){
                towar=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        "brakTowaru","k",0,0);
                model.addAttribute("towar",towar);
            }else{
                model.addAttribute("towar",towar);
            }
            //obsługa błędu bazy danych
        }catch (Exception e){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "bladPob","k",0,0);
            model.addAttribute("towar",t);
        }
        return"edytuj-towar";
    }

    //strona do przyjmowania post ze strony edycji towarów
    @PostMapping("/producent/edytuj-towar")
    public String edytujTowar(@ModelAttribute Towar towar, Model model){

        try{
            towarService.aktualizujTowar(towar);
        }catch(Exception e){
            return "redirect:/producent/edytuj-towar?idTowaru="+towar.getIdTowaru()+
                    "&message="+"Error: modyfikacja danych nieudana";
        }

        return "redirect:/producent/towary-stan";
    }

    //funkcja do wyświetlania strony dodawania towarów
    @GetMapping("/producent/add-towar")
    public String addTowarGet(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("producent", userDetails);
        model.addAttribute("towarDto", new TowarDto());
        return "add-towar";
    }

    //funkcja do odbierania post ze strony dodawania towarów
    @PostMapping("/producent/add-towar")
    public String addTowarPost(@ModelAttribute("towarDto") TowarDto towarDto, Model model, Principal principal){

        towarDto.setEmailProducenta(principal.getName());

        try{
            towarService.saveTowar(towarDto);
            model.addAttribute("message", "Towar dodany");

            //obsługa błędu bazy danych
        }catch (Exception e){
            model.addAttribute("message2", "Error: dodanie danych nieudane");
        }

        return "add-towar";
    }

    //funkcja do ładowania strony wysylki-magazyn
    @GetMapping("/producent/wysylki-magazyn")
    public ModelAndView wysylka_magazynGet(Principal principal){
        List<String> list=new ArrayList<>();

        try{
            list=magazynService.getProducentMagazyn(towarService.getProducentId(principal.getName()));

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                list.add("brakTowarow");
            }else if(list.get(0)==null){
                list.set(0,"brakTowarow");
            }

            //obsługa błędu bazy danych
        }catch (Exception e){
            list.add("bladPob");
        }

        return new ModelAndView("wysylki-magazyn","magazyn",list) ;
    }

    //funkcja do ładowania strony wysylki-towary
    @GetMapping("/producent/wysylki-towary")
    public ModelAndView wysylki_towaryGet(@RequestParam Long magazyn, Model model,Principal principal){

        List<Towar> list=new ArrayList<>();

        try{
            list=towarService.getTowaryByProducentAndMagazyn(towarService.getProducentId(principal.getName()),magazyn);

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        "brakTowarow","k",0,0);
                list.add(t);
            }else if(list.get(0)==null){
                Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        "brakTowarow","k",0,0);
                list.set(0,t);
            }

            //obsługa błędu bazy danych
        }catch (Exception e){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "bladPob","k",0,0);
            list.add(t);
        }

        model.addAttribute("magazyn",magazyn);
        return new ModelAndView("wysylki-towary","towar",list) ;
    }

    //funkcja do ładowania strony wysylki-towar
    @GetMapping("/producent/wysylki-towar")
    public String wysylki_towarGet(@RequestParam Long idTowaru, @RequestParam Long idMagazynu,
                                   @RequestParam String message, Model model){

        model.addAttribute("magazyn",idMagazynu);
        model.addAttribute("towarDostawaDto", new TowarDostawaDto());

        if(message.equals("1")){
            model.addAttribute("message","Dostawa wysłana");
        } else if (message.equals("2")) {
            model.addAttribute("message","Błąd przy próbie wysłania dostawy");
        }

        try{
            Towar towar=towarService.findByIdInMagazyn(idTowaru,idMagazynu);

            if(towar==null){
                towar=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        "brakTowaru","k",0,0);
                model.addAttribute("towar",towar);
            }else{
                model.addAttribute("towar",towar);
            }

            //obsługa błędu bazy danych
        }catch(Exception e){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "bladPob","k",0,0);
            model.addAttribute("towar",t);
        }
        return "wysylki-towar";
    }

    //funkcja do odbierania post ze strony wysylki-towar
    @PostMapping("/producent/wysylki-towar")
    public String wysylki_towarPost(@ModelAttribute("towarDostawaDto") TowarDostawaDto towarDostawaDto,
                                    Model model,Principal principal){

        String message="";

        try{
            towarService.saveWysylka(towarDostawaDto.getIdTowaru(),towarDostawaDto.getIdMagazynu(),
                    towarService.getProducentId(principal.getName()),towarDostawaDto.getAdres()
                    ,towarDostawaDto.getIloscWDostawie());

            //ustalenie wiadomosci zwrotnej
            // 1 - poprawne wysłanie dostawy
            message= "1";


            //obsługa błędu bazy danych
        }catch(Exception e){
            // 2 - błąd przy próbie wysłania dostawy
            message= "2";
        }

        return "redirect:wysylki-towar?idTowaru="+towarDostawaDto.getIdTowaru()+
                "&idMagazynu="+towarDostawaDto.getIdMagazynu()+"&message="+message;
    }

    //funkcja do wyświetlania strony wiadomosci
    @GetMapping("/producent/wiadomosci")
    public String getWiadomosci(){
        return "wiadomosci-wybor";
    }

    //funkcja do wyświetlania strony nieprzeczytanych wiadomosci
    @GetMapping("/producent/wiadomosci-aktywne")
    public ModelAndView getWiadomosciAktywne(Model model, Principal principal){

        List<Komunikat> list=new ArrayList<>();

        try{
            list=komunikatService.getAllUnreadKomunikat(principal.getName());

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        Integer.toUnsignedLong(1),"brakWartosci",0,"");
                list.add(t);
            }else if(list.get(0)==null){
                Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        Integer.toUnsignedLong(1),"brakWartosci",0,"");
                list.set(0,t);
            }

            //obsługa błędu bazy danych
        }catch (Exception e){
            Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    Integer.toUnsignedLong(1),"bladPob",0,"");
            list.add(t);
        }

        return new ModelAndView("wiadomosci","komunikaty",list) ;
    }

    //funkcja do ładowania strony wiadomosc-aktywna, nieprzeczytanej wiadomosci
    @GetMapping("/producent/wiadomosc-aktywna")
    public String getAktywnaWiadomosc(@RequestParam Long idKomunikatu, Model model){

        try{
            Komunikat komunikat=komunikatService.findKomunikat(idKomunikatu);

            if(komunikat==null){
                Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        Integer.toUnsignedLong(1),"brakWartosci",0,"");
                model.addAttribute("komunikat",komunikat);
            }else{

                //zmienienie statusu komunikatu na przeczytany
                if(komunikat.getCzyOdczytano()==0){
                    komunikatService.zaznaczJakoPrzeczytana(komunikat.getIdKomunikatu());
                }

                model.addAttribute("komunikat",komunikat);
            }

            //obsługa błędu bazy danych
        }catch(Exception e){
            e.printStackTrace();
            Komunikat k=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    Integer.toUnsignedLong(1),"bladPob",0,"");
            model.addAttribute("komunikat",k);
        }

        return "wiadomosc-aktywna";
    }

    //funkcja do wyświetlania strony przeczytanych wiadomosci
    @GetMapping("/producent/wiadomosci-nieaktywne")
    public ModelAndView getWiadomosciNieaktywne(Model model, Principal principal){

        List<Komunikat> list=new ArrayList<>();

        try{
            list=komunikatService.getAllReadKomunikat(principal.getName());

            //wypełnienie pustego elementu w celu uniknięcia wyjątków
            if(list.size() == 0){
                Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        Integer.toUnsignedLong(1),"brakWartosci",0,"");
                list.add(t);
            }else if(list.get(0)==null){
                Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        Integer.toUnsignedLong(1),"brakWartosci",0,"");
                list.set(0,t);
            }

            //obsługa błędu bazy danych
        }catch (Exception e){
            Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    Integer.toUnsignedLong(1),"bladPob",0,"");
            list.add(t);
        }

        return new ModelAndView("wiadomosci","komunikaty",list) ;
    }

    //funkcja do ładowania strony wiadomosc-aktywna, nieprzeczytanej wiadomosci
    @GetMapping("/producent/wiadomosc-nieaktywna")
    public String getNieaktywnaWiadomosc(@RequestParam Long idKomunikatu, Model model){

        try{
            Komunikat komunikat=komunikatService.findKomunikat(idKomunikatu);

            if(komunikat==null){
                Komunikat t=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                        Integer.toUnsignedLong(1),"brakWartosci",0,"");
                model.addAttribute("komunikat",komunikat);
            }else{
                model.addAttribute("komunikat",komunikat);
            }

            //obsługa błędu bazy danych
        }catch(Exception e){
            e.printStackTrace();
            Komunikat k=new Komunikat(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    Integer.toUnsignedLong(1),"bladPob",0,"");
            model.addAttribute("komunikat",k);
        }

        return "wiadomosc-nieaktywna";
    }
}
