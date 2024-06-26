package com.MagazynManagement.service;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZadanieService {

    @Autowired
    private ZadanieRepozytory zadanieRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private SamochodDostawczyRepository samochodDostawczyRepository;

    @Autowired
    private ZamowienieRepository zamowienieRepository;

    @Autowired
    private DostawaRepository dostawaRepository;

    public List<Uzytkownik> getMagazynier(Long magazyn){
        return uzytkownikRepository.findMagazynier(magazyn);
    }

    public Long getKierownikId(String email){
        return uzytkownikRepository.findKierownik(email);
    }

    public List<Zadanie> getZadanie(){
        return zadanieRepository.findByIdPracownika(null);
    }

    public Long getMagazynByKierownik(Long id){
        return uzytkownikRepository.findMagazynByKierownik(id);
    }

    public Zadanie getZadanieById(Long id){return zadanieRepository.getReferenceById(id);}

    public List<Zadanie> getZadanieByManager(Long id){
        return zadanieRepository.findByKierownik(id);
    }

    public void anulujZadanie(Long id){
        zadanieRepository.anulujZadanie(id);
    }

    public void zapiszZadanie(Zadanie zadanie){
        zadanieRepository.save(zadanie);
    }

    public void updateZadanie(Zadanie zadanie){
        Zadanie og = zadanieRepository.getReferenceById(zadanie.getId_zadania());
        og.setId_pracownika(zadanie.getId_pracownika());
        og.setOpis(zadanie.getOpis());
        og.setStatus(zadanie.getStatus());
        zadanieRepository.save(og);
    }

    /*public void wykonajZadanie(Long id, String status){
        Zadanie zadanie = zadanieRepository.getReferenceById(id);
        zadanie.setStatus(status);
        zadanieRepository.save(zadanie);
        Zamowienie zamowienie = zamowienieRepository.getReferenceById(zadanie.getNr_zamowienia());
        Dostawa dostawa = zamowienie.getDostawa();
        dostawa.setStatus("Dostarczone");
        dostawaRepository.save(dostawa);
    }*/

    public void dodajZadanie(Zadanie zadanie){
        zadanieRepository.save(zadanie);
    }

    public List<SamochodDostawczy> getAllSamochodDostawczy(){return samochodDostawczyRepository.findAll();}

    public List<Zadanie> getZadanieByPracownikId(Long id){
        return zadanieRepository.findByIdPracownika(id);
    }

    public List<Zadanie> getDoPrzydzielenia(Long id){
        List<Zadanie> l = zadanieRepository.findDoPrzydzielenia();
        List<Zadanie> lista = new ArrayList<>();
        for (Zadanie z : l){
            if (z.getOpis().contains("Magazyn "+id)){
                lista.add(z);
            }
        }
        return lista;
    }
}
