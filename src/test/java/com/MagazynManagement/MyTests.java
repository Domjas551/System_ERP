package com.MagazynManagement;

import com.MagazynManagement.entity.*;
import com.MagazynManagement.repository.*;
import com.MagazynManagement.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MyTests {
    @Mock
    private DostawaRepository dostawaRepository;

    @InjectMocks
    private DostawaService dostawaService;

    @Mock
    private KlientRepository klientRepository;

    @InjectMocks
    private KlientService klientService;

    @Mock
    private KomunikatRepository komunikatRepository;

    @InjectMocks
    private KomunikatService komunikatService;

    @Mock
    private MagazynRepository magazynRepository;

    @InjectMocks
    private MagazynService magazynService;
    @Mock
    private PozycjaOfertyRepository pozycjaOfertyRepository;

    @InjectMocks
    private PozycjaOfertyService ofertaService;

    @Mock
    private TowarMagazynRepository towarMagazynRepository;

    @InjectMocks
    private TowarMagazynService towarMagazynService;
    @Mock
    private TowarRepository towarRepository;

    @InjectMocks
    private TowarService towarService;
    @Mock
    private WysylkaRepository wysylkaRepository;

    @InjectMocks
    private WysylkaService wysylkaService;
    @Mock
    private ZadanieRepozytory zadanieRepository;

    @InjectMocks
    private ZadanieService zadanieService;

    @Test
    void testGetKierowcaId() {
        String email = "example@example.com";
        Long expectedKierowcaId = 1L;
        when(dostawaRepository.findKierowcaId(email)).thenReturn(expectedKierowcaId);

        Long actualKierowcaId = dostawaService.getKierowcaId(email);

        assertEquals(expectedKierowcaId, actualKierowcaId);
    }

    @Test
    void testFindAllActiveDostawaByKierowcaId() throws Exception {
        Long kierowcaId = 1L;
        List<Dostawa> expectedDostawy = new ArrayList<>();
        expectedDostawy.add(new Dostawa(1L, "typ1", "adres1", "status1"));
        expectedDostawy.add(new Dostawa(2L, "typ2", "adres2", "status2"));
        expectedDostawy.add(new Dostawa(3L, "typ3", "adres3", "status3"));

        when(dostawaRepository.findAllActiveByKierowcaId(kierowcaId)).thenReturn(expectedDostawy);

        List<Dostawa> actualDostawy = dostawaService.findAllActiveDostawaByKierowcaId(kierowcaId);

        assertEquals(expectedDostawy, actualDostawy);
    }


    @Test
    void testFindByExistingEmail() {
        String existingEmail = "example@example.com";
        Klient expectedKlient = new Klient("John", "Doe", existingEmail, "Adres", "123456789");

        when(klientRepository.findByEmail(existingEmail)).thenReturn(Optional.of(expectedKlient));

        Klient actualKlient = klientService.findByEmail(existingEmail);

        assertEquals(expectedKlient, actualKlient);
    }

    @Test
    void testFindByNonExistingEmail() {
        String nonExistingEmail = "nonexisting@example.com";

        when(klientRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> klientService.findByEmail(nonExistingEmail));
    }


    @Test
    void testFindKomunikat() throws Exception {
        Long idKomunikatu = 1L;
        Komunikat expectedKomunikat = new Komunikat(idKomunikatu, 1L, 2L, "Tresc komunikatu", 0, "2024-06-04");

        when(komunikatRepository.findKomunikat(idKomunikatu)).thenReturn(expectedKomunikat);

        Komunikat actualKomunikat = komunikatService.findKomunikat(idKomunikatu);

        assertEquals(expectedKomunikat, actualKomunikat);
    }

    @Test
    void testZaznaczJakoPrzeczytana() throws Exception {
        Long idKomunikatu = 1L;

        komunikatService.zaznaczJakoPrzeczytana(idKomunikatu);

        verify(komunikatRepository, times(1)).zmienNaPrzeczytano(idKomunikatu);
    }

    @Test
    void testAddKomunikat() throws Exception {
        Long idNadawcy = 1L;
        Long idOdbiorcy = 2L;
        String tresc = "Nowy komunikat";
        int czyOdczytano = 0;
        String data = "2024-06-04";

        komunikatService.addKomunikat(idNadawcy, idOdbiorcy, tresc, czyOdczytano, data);

        verify(komunikatRepository, times(1)).addKomunikat(idNadawcy, idOdbiorcy, tresc, czyOdczytano, data);
    }


    @Test
    void testGetMagazyn() {
        Long idMagazynu = 1L;
        Magazyn expectedMagazyn = new Magazyn(idMagazynu, "Adres magazynu");

        when(magazynRepository.findById(idMagazynu)).thenReturn(Optional.of(expectedMagazyn));

        Magazyn actualMagazyn = magazynService.getMagazyn(idMagazynu);

        assertEquals(expectedMagazyn, actualMagazyn);
    }

    @Test
    void testPobierzOferte() {
        // Przykładowe dane zwracane przez repozytorium
        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"Nazwa1", "Producent1", "Kategoria1"});
        mockResults.add(new Object[]{"Nazwa2", "Producent2", "Kategoria2"});

        // Oczekiwana lista obiektów PozycjaOferty
        List<PozycjaOferty> expectedOferta = new ArrayList<>();
        expectedOferta.add(new PozycjaOferty("Nazwa1", "Producent1", "Kategoria1"));
        expectedOferta.add(new PozycjaOferty("Nazwa2", "Producent2", "Kategoria2"));

        // Konfigurujemy zachowanie mocka
        when(pozycjaOfertyRepository.getOferta()).thenReturn(mockResults);

        // Wywołujemy testowaną metodę
        List<PozycjaOferty> actualOferta = ofertaService.pobierzOferte();

        // Sprawdzamy, czy rozmiar listy jest taki sam
        assertEquals(expectedOferta.size(), actualOferta.size(), "Rozmiar list nie jest taki sam");

        // Sprawdzamy, czy zawartość list jest taka sama
        for (int i = 0; i < expectedOferta.size(); i++) {
            PozycjaOferty expectedPozycja = expectedOferta.get(i);
            PozycjaOferty actualPozycja = actualOferta.get(i);

            assertEquals(expectedPozycja.getNazwa(), actualPozycja.getNazwa(), "Nieprawidłowa nazwa w pozycji " + i);
            assertEquals(expectedPozycja.getProducent(), actualPozycja.getProducent(), "Nieprawidłowy producent w pozycji " + i);
            assertEquals(expectedPozycja.getKategoria(), actualPozycja.getKategoria(), "Nieprawidłowa kategoria w pozycji " + i);
        }
    }

    @Test
    void testPobierzTowarMagazynDlaMagazynu() throws Exception {
        Long idMagazynu = 1L;
        List<Object[]> mockedResults = new ArrayList<>();
        mockedResults.add(new Object[]{1, 1, "Nazwa1", "Producent1", "Kategoria1", 10});

        when(towarMagazynRepository.findByMagazyn_IdMagazynu(idMagazynu)).thenReturn(mockedResults);

        List<TowarMagazyn> expectedTowaryMagazyn = new ArrayList<>();
        expectedTowaryMagazyn.add(new TowarMagazyn(1L, 1L, "Nazwa1", "Producent1", "Kategoria1", 10));

        List<TowarMagazyn> actualTowaryMagazyn = towarMagazynService.pobierzTowarMagazynDlaMagazynu(idMagazynu);

        assertEquals(expectedTowaryMagazyn.size(), actualTowaryMagazyn.size());

        for (int i = 0; i < expectedTowaryMagazyn.size(); i++) {
            TowarMagazyn expected = expectedTowaryMagazyn.get(i);
            TowarMagazyn actual = actualTowaryMagazyn.get(i);

            assertEquals(expected.getIdTowaru(), actual.getIdTowaru());
            assertEquals(expected.getIdMagazynu(), actual.getIdMagazynu());
            assertEquals(expected.getNazwa(), actual.getNazwa());
            assertEquals(expected.getProducent(), actual.getProducent());
            assertEquals(expected.getKategoria(), actual.getKategoria());
            assertEquals(expected.getIlosc(), actual.getIlosc());
        }
    }

    @Test
    void testAktualizujTowarExists() throws Exception {
        // Przygotowanie danych wejściowych
        Towar towar = new Towar(1L, 1L, "NazwaTowaru", "Kategoria", 10, 20);

        // Symulacja istnienia towaru w repozytorium
        when(towarRepository.existsById(towar.getIdTowaru())).thenReturn(true);

        // Wywołanie metody testowanej
        assertDoesNotThrow(() -> towarService.aktualizujTowar(towar));
    }

    @Test
    void testAktualizujTowarNotExists() {
        // Przygotowanie danych wejściowych
        Towar towar = new Towar(1L, 1L, "NazwaTowaru", "Kategoria", 10, 20);

        // Symulacja nieistnienia towaru w repozytorium
        when(towarRepository.existsById(towar.getIdTowaru())).thenReturn(false);

        // Wywołanie metody testowanej i sprawdzenie czy zwraca wyjątek
        assertThrows(Exception.class, () -> towarService.aktualizujTowar(towar));
    }

    @Test
    void testDodajWysylke() {
        // Przygotowanie danych wejściowych
        Wysylka wysylka = new Wysylka(1L, 1L, 2L, 3L, "Nowa", 2, "2024-06-04", "Adres wysyłki");

        // Symulacja działania repozytorium
        doNothing().when(wysylkaRepository).dodajWysylke(wysylka.getId_klienta_detalicznego(), wysylka.getId_klienta_hurtowego(), wysylka.getId_kierowcy(), wysylka.getData(), wysylka.getInterwal(), wysylka.getAdres(), wysylka.getStatus());

        // Wywołanie metody testowanej
        assertDoesNotThrow(() -> wysylkaService.dodajWysylke(wysylka));

        // Sprawdzenie, czy metoda repozytorium została wywołana z poprawnymi argumentami
        verify(wysylkaRepository, times(1)).dodajWysylke(wysylka.getId_klienta_detalicznego(), wysylka.getId_klienta_hurtowego(), wysylka.getId_kierowcy(), wysylka.getData(), wysylka.getInterwal(), wysylka.getAdres(), wysylka.getStatus());
    }

    @Test
    void testUpdateZadanie() {
        // Przygotowanie danych wejściowych
        Zadanie zadanie = new Zadanie(1L, 1L, 2L, "Przykładowy opis", "oczekujące");

        // Stworzenie zadania "oryginalnego" w repozytorium
        when(zadanieRepository.getReferenceById(zadanie.getId_zadania())).thenReturn(zadanie);

        // Wywołanie metody testowanej
        assertDoesNotThrow(() -> zadanieService.updateZadanie(zadanie));

        // Sprawdzenie, czy zadanie zostało poprawnie zaktualizowane w repozytorium
        verify(zadanieRepository, times(1)).save(zadanie);
    }
}
