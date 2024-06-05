package com.MagazynManagement.controller;

import com.MagazynManagement.dto.PracownikDto;
import com.MagazynManagement.entity.Komunikat;
import com.MagazynManagement.entity.TowarWysylka;
import com.MagazynManagement.entity.Uzytkownik;
import com.MagazynManagement.entity.Wysylka;
import com.MagazynManagement.repository.KomunikatRepository;
import com.MagazynManagement.repository.UzytkownikRepository;
import com.MagazynManagement.service.TowarWysylkaService;
import com.MagazynManagement.service.UzytkownikService;
import com.MagazynManagement.service.WysylkaService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class AdministratorController {


    private final UzytkownikService uzytkownikService;

    private final UserDetailsService userDetailsService;

    private final WysylkaService wysylkaService;

    private final TowarWysylkaService towarWysylkaService;

    private final KomunikatRepository komunikatRepository;

    private final UzytkownikRepository uzytkownikRepository;


    @GetMapping("/admin")
    public String admin(Model model, HttpSession session, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("admin", userDetails);
        model.addAttribute("messageCykl",session.getAttribute("messageCykl"));
        session.setAttribute("messageCykl",null);
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

    //Wykonanie zaległych wysyłek cyklicznych
    @GetMapping("/admin/cykliczne")
    public String updateCyclic(Model model, HttpSession session) throws ParseException {
        List<Wysylka> wcl = wysylkaService.findWysylkaCykliczna(); //Pobranie wszystkich wysyłek cyklicznych z bazy
        List<TowarWysylka> twcl=new ArrayList<>();

        int count =0;

        String dataString;
        Date data;
        Date curData;
        Integer odOstatniego;
        Wysylka wysylka;
        Long newWysylkaId;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy"); //Ustalony format zgodny z danymi przetrzymywanymi w bazie

        for(int i=0; i<wcl.size(); i++)
        {
            curData=new Date();
            curData.setTime(curData.getTime()+i*1000);
            dataString = wcl.get(i).getData();
            data=dateFormat.parse(dataString);
            odOstatniego=(int)((curData.getTime()-data.getTime()) / (1000 * 60 * 60 * 24));

            if(odOstatniego>=wcl.get(i).getInterwal()) //Sprawdzenie, czy wysyłka cykliczna  powinna zostać już ponowiona
            {
                if(wcl.get(i).getId_klienta_detalicznego()==null)
                {
                    wysylka=new Wysylka(null, wcl.get(i).getId_klienta_hurtowego(), null,null,"niezatwierdzona",wcl.get(i).getInterwal(),dateFormat.format(curData),wcl.get(i).getAdres());
                }
                else
                {
                    wysylka=new Wysylka(null,null, wcl.get(i).getId_klienta_detalicznego(), null,"niezatwierdzona",wcl.get(i).getInterwal(),dateFormat.format(curData),wcl.get(i).getAdres());
                }
                wysylkaService.dodajWysylke(wysylka); //Dodanie do bazy nowej wysyłki cyklicznej stanowiącej ponowienie zaległej
                wysylkaService.nullifyInterwal(wcl.get(i).getId_wysylki()); //Uczynienie poprzedniej instancji powtarzanej wysyłki niecykliczną

                twcl=towarWysylkaService.findTowarIdByWysylkaID(wcl.get(i).getId_wysylki()); //Pobranie wszystkich towarów i ilości przypisanych do starej instancji powtarzanej wysyłki

                if(wcl.get(i).getId_klienta_detalicznego()==null)
                {
                    newWysylkaId =wysylkaService.findPrzesylkeUzytkownika(wcl.get(i).getId_klienta_hurtowego(),dateFormat.format(curData));
                }
                else
                {
                    newWysylkaId=wysylkaService.findPrzesylkeUzytkownika(wcl.get(i).getId_klienta_detalicznego(),dateFormat.format(curData));
                }
                towarWysylkaService.dodajTowarWysylkiCykl(twcl,newWysylkaId); //Dodanie skopiowanych z poprzedniczki towarów do nowej wysyłki

                count++;
            }
        }
        //Przygotowanie wiadomości o efektach aktualizacji
        if(count==0)
        {
            session.setAttribute("messageCykl", "Żadne cykliczne zamówienie nie wymagało ponowienia.");
        }
        else
        {
            session.setAttribute("messageCykl", "Aktualizacja udana! Ponowiono "+count+" cykliczne zamówienia.");
        }

        return "redirect:/admin";
    }


    @GetMapping("/admin/komunikaty")
    public String showAllKomunikaty(Model model){
        List<Komunikat> komunikaty = komunikatRepository.findAll();

        Map<Long, String> userEmails = new HashMap<>();
        for (Komunikat komunikat : komunikaty) {
            if (!userEmails.containsKey(komunikat.getIdNadawcy())) {
                Uzytkownik nadawca = uzytkownikRepository.findById(komunikat.getIdNadawcy()).orElse(null);
                userEmails.put(komunikat.getIdNadawcy(), nadawca != null ? nadawca.getEmail() : "Nieznany");
            }
            if (!userEmails.containsKey(komunikat.getIdOdbiorcy())) {
                Uzytkownik odbiorca = uzytkownikRepository.findById(komunikat.getIdOdbiorcy()).orElse(null);
                userEmails.put(komunikat.getIdOdbiorcy(), odbiorca != null ? odbiorca.getEmail() : "Nieznany");
            }
        }

        model.addAttribute("komunikaty", komunikaty);
        model.addAttribute("userEmails", userEmails);
        return "komunikaty";
    }
}
