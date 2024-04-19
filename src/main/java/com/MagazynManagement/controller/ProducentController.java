package com.MagazynManagement.controller;

import com.MagazynManagement.dto.ProducentDto;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.service.TowarService;
import com.MagazynManagement.service.UzytkownikService;
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
import java.util.List;

@Controller
public class ProducentController {

    ErrorController er= new ErrorController();

    @Autowired
    TowarService towarService;

    @Autowired
    UzytkownikService uzytkownikService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/producent")
    public String producent(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("producent", userDetails);
        return "producent-main";
    }

    @GetMapping("/producent/towary-stan")
    public ModelAndView stanProduktow(Model model, Principal principal){
        List<Towar> list=towarService.getAllTowarByProducentId(principal.getName());

        //wypełnienie pustego elementu w celu uniknięcia wyjątków
        if(list.get(0)==null){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "brakTowarow","k",0,0);
            list.set(0,t);
        }

        return new ModelAndView("towary-stan","towar",list) ;
    }

    @GetMapping("/producent/edytuj-towar")
    public String edytujTowarForm(@RequestParam Long idTowaru, Model model){
        Towar towar=towarService.findById(idTowaru);

        if(towar==null){
            er.error("Nie znaleziono towaru","/producent/towary-stan",model);
        }else{
            model.addAttribute("towar",towar);
            return"edytuj-towar";
        }
        return "error";
    }

    @PostMapping("/producent/edytuj-towar")
    public String edytujTowar(@ModelAttribute Towar towar, Model model){
        String w=towarService.aktualizujTowar(towar);

        if(w.equals("error")){
            er.error("Błąd przy próbie modyfikacji","towary-stan",model);
        }else{
            return "redirect:/producent/towary-stan";
        }
        return "error";
    }
}
