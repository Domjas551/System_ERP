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

    public void clear()
    {
        for(int i=0;i<stany.size();i++)
        {
            for(int j=0;j<stany.get(i).size();j++) {
                stany.get(i).set(j,0);
            }
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


