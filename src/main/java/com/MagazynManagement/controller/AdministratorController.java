package com.MagazynManagement.controller;

import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.entity.Pracownik;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.service.UzytkownikService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdministratorController {


    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;


    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("admin", userDetails);
        return "admin-main";
    }

    @GetMapping("/admin/add-pracownik")
    public String addPracownikPage(Model model){
        model.addAttribute("pracownikDto", new PracownikDto());
        return "add-pracownik";
    }

    @PostMapping("/admin/add-pracownik")
    public String savePracownik(@ModelAttribute("pracownikDto") PracownikDto pracownikDto, Model model){
        Uzytkownik savedUzytkownik = uzytkownikService.savePracownik(pracownikDto);
        model.addAttribute("message", "Pracownik dodany");
        return "add-pracownik";
    }


    @GetMapping("/admin/klienci")
    public String showClients(@RequestParam(value = "type", required = false) String type,
                              Model model, Principal principal){
        if(type == null)
            type = "detaliczny";

        List<Uzytkownik> users = uzytkownikService.getUserByRole("client", type, principal.getName());

        model.addAttribute("users", users);
        model.addAttribute("selectedType", type);

        return "clients-list";
    }


    @GetMapping("/admin/pracownicy")
    public String showEmployees(Model model, Principal principal){
        List<Uzytkownik> users = uzytkownikService.getUserByRole("employee", null, principal.getName());
        model.addAttribute("users", users);
        return "employees-list";
    }


    @GetMapping("/admin/producenci")
    public String showProducers(Model model, Principal principal){
        List<Uzytkownik> users = uzytkownikService.getUserByRole("producer", null, principal.getName());
        model.addAttribute("users", users);
        return "producers-list";
    }

    @GetMapping("/admin/accountDetails/{id}")
    public String showAccDetails(@PathVariable Long id, Model model){
        Uzytkownik uzytkownik = uzytkownikService.getUserById(id);
        model.addAttribute("user", uzytkownik);

        return "account-details";
    }


    @PostMapping("/admin/accountDetails/{id}")
    public String updateAccount(@PathVariable("id") Long id,
                                @ModelAttribute("user") Uzytkownik uzytkownik,
                                @RequestParam("action") String action){
        Uzytkownik existingUser = uzytkownikService.getUserById(id);

        existingUser.setImie(uzytkownik.getImie());
        existingUser.setNazwisko(uzytkownik.getNazwisko());
        existingUser.setEmail(uzytkownik.getEmail());
        existingUser.setTelefon(uzytkownik.getTelefon());
        existingUser.setStanowisko(uzytkownik.getStanowisko());
        existingUser.setPensja(uzytkownik.getPensja());
        if(!uzytkownik.getHaslo().isEmpty() && uzytkownik.getHaslo() != null){
            uzytkownikService.updatePassword(existingUser, uzytkownik.getHaslo());
        }

        if("block".equals(action)){
            existingUser.setCzyAktywny(false);
        }
        else if("unblock".equals(action)){
            existingUser.setCzyAktywny(true);
        }

        uzytkownikService.updateUser(existingUser);

        return "redirect:/admin/accountDetails/" + id;
    }
}
