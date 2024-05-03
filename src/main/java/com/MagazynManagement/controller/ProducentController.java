package com.MagazynManagement.controller;

import com.MagazynManagement.dto.TowarDostawaDto;
import com.MagazynManagement.dto.TowarDto;
import com.MagazynManagement.entity.Magazyn;
import com.MagazynManagement.entity.Towar;
import com.MagazynManagement.service.MagazynService;
import com.MagazynManagement.service.TowarService;
import com.MagazynManagement.service.UzytkownikService;
import org.apache.coyote.Request;
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

    //zmienna dla kontrolera errorów
    ErrorController er= new ErrorController();

    @Autowired
    TowarService towarService;

    @Autowired
    MagazynService magazynService;

    @Autowired
    UserDetailsService userDetailsService;

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
        List<Towar> list=towarService.getAllTowarByProducentId(principal.getName());

        //wypełnienie pustego elementu w celu uniknięcia wyjątków
        if(list.get(0)==null){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "brakTowarow","k",0,0);
            list.set(0,t);
        }

        return new ModelAndView("towary-stan","towar",list) ;
    }

    //funkcja do wyświetlania strony edycji towarów
    @GetMapping("/producent/edytuj-towar")
    public String edytujTowarForm(@RequestParam Long idTowaru, Model model){
        Towar towar=towarService.findById(idTowaru);

        if(towar==null){
            er.error("Nie znaleziono towaru","/producent/towary-stan",model);
        }else{
            model.addAttribute("towar",towar);
            return"edytuj-towar";
        }
        return "errorPage";
    }

    //strona do przyjmowania post ze strony edycji towarów
    @PostMapping("/producent/edytuj-towar")
    public String edytujTowar(@ModelAttribute Towar towar, Model model){
        String w=towarService.aktualizujTowar(towar);

        if(w.equals("error")){
            er.error("Błąd przy próbie modyfikacji","towary-stan",model);
        }else{
            return "redirect:/producent/towary-stan";
        }
        return "errorPage";
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

        towarService.saveTowar(towarDto);

        model.addAttribute("message", "Towar dodany");
        return "add-towar";
    }

    //funkcja do ładowania strony wyslki-magazyn
    @GetMapping("/producent/wysylki-magazyn")
    public ModelAndView wysylka_magazynGet(Principal principal){
        List<String> list=magazynService.getProducentMagazyn(towarService.getProducentId(principal.getName()));

        return new ModelAndView("wysylki-magazyn","magazyn",list) ;
    }

    //funkcja do ładowania strony wysylki-towary
    @GetMapping("/producent/wysylki-towary")
    public ModelAndView wysylki_towaryGet(@RequestParam Long magazyn, Model model,Principal principal){

        List<Towar> list=towarService.getTowaryByProducentAndMagazyn(towarService.getProducentId(principal.getName()),magazyn);

        //wypełnienie pustego elementu w celu uniknięcia wyjątków
        if(list.get(0)==null){
            Towar t=new Towar(Integer.toUnsignedLong(1),Integer.toUnsignedLong(1),
                    "brakTowarow","k",0,0);
            list.set(0,t);
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
        }

        Towar towar=towarService.findByIdInMagazyn(idTowaru,idMagazynu);

        if(towar==null){
            er.error("Nie znaleziono towaru","/producent/towary-stan",model);
        }else{
            model.addAttribute("towar",towar);
            return "wysylki-towar";
        }
        return "errorPage";
    }

    //funkcja do odbierania post ze strony wysylki-towar
    @PostMapping("/producent/wysylki-towar")
    public String wysylki_towarPost(@ModelAttribute("towarDostawaDto") TowarDostawaDto towarDostawaDto,Model model){

        System.out.println(towarDostawaDto.getIloscWDostawie());
        System.out.println(towarDostawaDto.getIdMagazynu());
        System.out.println(towarDostawaDto.getIdTowaru());
        String message= "1";

        return "redirect:wysylki-towar?idTowaru="+towarDostawaDto.getIdTowaru()+
                "&idMagazynu="+towarDostawaDto.getIdMagazynu()+"&message="+message;
    }
}
