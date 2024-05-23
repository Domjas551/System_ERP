package com.MagazynManagement.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {


    private Uzytkownik uzytkownik;


    public CustomUserDetails(Uzytkownik uzytkownik){
        this.uzytkownik = uzytkownik;
    }


    public String getImie(){
        return uzytkownik.getImie();
    }

    public String getNazwaFirmy(){
        return uzytkownik.getNazwaFirmy();
    }


    public Long getId(){
        return uzytkownik.getIdUzytkownika();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.singletonList(new SimpleGrantedAuthority(uzytkownik.getRola()));
    }


    @Override
    public String getPassword(){
        return uzytkownik.getHaslo();
    }


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
        return uzytkownik.isCzyAktywny();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return uzytkownik.isCzyAktywny();
    }


    public Uzytkownik getUzytkownik(){
        return uzytkownik;
    }
}
