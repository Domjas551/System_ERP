package com.MagazynManagement.entity;

import java.util.ArrayList;

public class StanMagazynowSesja
{
    private ArrayList<ArrayList<Integer>> stany;

    public StanMagazynowSesja(int liczbaMagazynow)
    {
        this.stany= new ArrayList<>(liczbaMagazynow);
        for(int i=0;i<liczbaMagazynow;i++)
        {
            stany.add(new ArrayList());
        }
    }

    public ArrayList<ArrayList<Integer>> getStany() {
        return stany;
    }

    public void setStany(ArrayList<ArrayList<Integer>> stany) {
        this.stany = stany;
    }

    public void inicjujMagazyn(int numerMagazynu, int liczbaTowarow)
    {
            for(int j=0;j<liczbaTowarow;j++) {
                stany.get(numerMagazynu).add(0);
            }
    }

    @Override
    public String toString() {
        return "StanMagazynowSesja{" +
                "stany=" + stany +
                '}';
    }
}


