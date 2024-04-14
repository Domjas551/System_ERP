package com.MagazynManagement.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    //private Konto konto;


    private Uzytkownik uzytkownik;

    /*public CustomUserDetails(Konto konto) {
        this.konto = konto;
    }*/
    public CustomUserDetails(Uzytkownik uzytkownik){
        this.uzytkownik = uzytkownik;
    }

    /*public String getImieKlient(){
        return konto.getKlient().getImie();
    }*/
    public String getImie(){
        return uzytkownik.getImie();
    }

    public String getNazwaFirmy(){
        return uzytkownik.getNazwaFirmy();
    }

    /*public String getImiePracownik(){
        return konto.getPracownik().getImie();
    }*/

//    public Long getIdPracownika(){return konto.getPracownik().getIdPracownika();}
    public Long getId(){
        return uzytkownik.getIdUzytkownika();
    }


    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(konto.getRola()));
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singletonList(new SimpleGrantedAuthority(uzytkownik.getRola()));
    }

    /*@Override
    public String getPassword() {
        return konto.getHaslo();
    }*/

    @Override
    public String getPassword(){
        return uzytkownik.getHaslo();
    }

    /*@Override
    public String getUsername() {
        return konto.getLogin();
    }*/

    @Override
    public String getUsername(){
        return uzytkownik.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return uzytkownik.isCzyAktywny();
    }

//    public Konto getKonto() {
//        return konto;
//    }
    public Uzytkownik getUzytkownik(){
        return uzytkownik;
    }
}
