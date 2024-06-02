package com.MagazynManagement.controller;

import com.MagazynManagement.dto.ProducentDto;
import com.MagazynManagement.dto.UserDto;
import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.UzytkownikRepository;
import com.MagazynManagement.service.TowarService;
import com.MagazynManagement.service.TowarWysylkaService;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.WysylkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UzytkownikCotroller {


    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;

    private final WysylkaService wysylkaService;

    private final UzytkownikRepository uzytkownikRepository;

    private final TowarWysylkaService towarWysylkaService;

    private final TowarService towarService;


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("userDto") UserDto userDto, Model model){
        if (!userDto.isCzyKlientDetaliczny() && !userDto.isCzyKlientHurtowy()) {
            // Obsługa błędu - żaden checkbox nie został zaznaczony
            model.addAttribute("error", "Musisz zaznaczyć przynajmniej jedną opcję");
            return "register";
        } else if (userDto.isCzyKlientDetaliczny() && userDto.isCzyKlientHurtowy()) {
            // Obsługa błędu - nie można wybrać jednocześnie klienta detalicznego i hurtowego
            model.addAttribute("error", "Nie można wybrać jednocześnie klienta detalicznego i hurtowego");
            return "register";
        }

        // Ustawienie wartości logicznych w zależności od wyboru użytkownika
        if (userDto.isCzyKlientDetaliczny()) {
            userDto.setCzyKlientHurtowy(false);
        } else if (userDto.isCzyKlientHurtowy()) {
            userDto.setCzyKlientDetaliczny(false);
        }

        Uzytkownik uzytkownik = uzytkownikService.saveUser(userDto);
        model.addAttribute("message", "Zostałeś zarejestrowany");
        return "register";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("klient", userDetails);

        Uzytkownik uzytkownik = uzytkownikRepository.findUserByEmail(principal.getName());
        List<Wysylka> WysylkaList = wysylkaService.findWysylkaByIdKlienta(uzytkownik.getIdUzytkownika()); //Pobranie z bazy wszystkich wysyłek klienta

        List<String> zawartosci= new ArrayList<>();
        List<TowarWysylka> ListTowarWysylka=new ArrayList<>();
        Long id;
        String pom;
        for(int i=0; i<WysylkaList.size(); i++)
        {
            id=WysylkaList.get(i).getId_wysylki();
            ListTowarWysylka=towarWysylkaService.findTowarIdByWysylkaID(id); //Pobranie wszystkich towarów wchodzacych w skałd wysyłki
            pom="";

            //Generowanie spisu towarów dla wysyłki
            for(int j=0; j<ListTowarWysylka.size(); j++)
            {
                if(j==0)
                {
                    pom = "- "+towarService.findById(ListTowarWysylka.get(j).getId_towaru()).getNazwa()+" "+ListTowarWysylka.get(j).getIlosc()+" [T]";
                }
                else
                {
                    pom =pom + "<br/>- "+towarService.findById(ListTowarWysylka.get(j).getId_towaru()).getNazwa()+" "+ListTowarWysylka.get(j).getIlosc()+" [T]";
                }
            }
            zawartosci.add(i,pom);
        }
        List<WysylkaView> wysylkaPom=new ArrayList<>();
        for(int i=0; i<WysylkaList.size(); i++)
        {
            wysylkaPom.add(new WysylkaView(WysylkaList.get(i),zawartosci.get(i))); //Generowanie listy wysyłek z ich zawartościami
        }

        model.addAttribute("wysylki", wysylkaPom);
        return "user-main";
    }


    @GetMapping("/registration-producent")
    public String getProducentRegisterPage(Model model){
        model.addAttribute("producentDto", new ProducentDto());
        return "register-producent";
    }

    @PostMapping("/registration-producent")
    public String saveProducent(@ModelAttribute("producentDto") ProducentDto producentDto, Model model){
        Uzytkownik uzytkownik = uzytkownikService.saveProducent(producentDto);
        model.addAttribute("message", "Zostałeś zarejestrowany");
        return "register-producent";
    }
}
