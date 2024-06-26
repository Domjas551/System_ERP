package com.MagazynManagement.service;

import com.MagazynManagement.dto.DostawaDto;
import com.MagazynManagement.entity.Dostawa;
import com.MagazynManagement.repository.DostawaRepository;
import jakarta.transaction.Transactional;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DostawaService {

    @Autowired
    DostawaRepository dostawaRepository;

    public Long getKierowcaId(String email){
        return dostawaRepository.findKierowcaId(email);
    }

    public List<Dostawa> findAllActiveDostawaByKierowcaId(Long idKierowcy) throws Exception {
            return dostawaRepository.findAllActiveByKierowcaId(idKierowcy);
    }

    public List<Dostawa> findAllNotActiveDostawaByKierowcaId(Long idKierowcy) throws Exception {
        return dostawaRepository.findAllNotActiveByKierowcaId(idKierowcy);
    }

    public Dostawa findWysylkaById(Long id) throws Exception{
        return dostawaRepository.findWysylkaById(id);
    }

    public Dostawa findDostawaById(Long id) throws Exception{
        return dostawaRepository.findDostawaById(id);
    }

    public Dostawa findNieaktywnaWysylkaById(Long id) throws Exception{
        return dostawaRepository.findNieaktywnaWysylkaById(id);
    }

    public Dostawa findNieaktywnaDostawaById(Long id) throws Exception{
        return dostawaRepository.findNieaktywnaDostawaById(id);
    }

    @Transactional
    public void aktualizujStatusWysylka(Long idDostawy, String status) throws Exception{
        dostawaRepository.zapiszStatusWysylka(idDostawy,status);
    }
    @Transactional
    public void aktualizujStatusDostawy(Long idDostawy, String status) throws Exception{
        dostawaRepository.zapiszStatusDostawa(idDostawy,status);
    }

    public List<Long> findDostawaBezKierowcy(Long id){
        return dostawaRepository.findDostawaBezKierowcy(id);
    }

}
