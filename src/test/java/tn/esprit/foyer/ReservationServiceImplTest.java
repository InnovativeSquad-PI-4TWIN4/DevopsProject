package tn.esprit.foyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.ReservationRepository;
import tn.esprit.foyer.services.ReservationServicImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
 class ReservationServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServicImpl reservationService;

    private Etudiant etudiant;
    private Chambre chambre;
    private Reservation reservation;

    @BeforeEach

    public void setUp() {
        etudiant = new Etudiant();
        etudiant.setCin(12345L);
        etudiant.setNomEt("John");
        etudiant.setPrenomEt("Doe");

        chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setReservations(new ArrayList<>());

        reservation = new Reservation();
        reservation.setIdReservation(1011234512023L); // ✅ Long, plus String
        reservation.setEstValid(true);
        reservation.setAnneeUniversitaire(LocalDate.of(2023, 9, 1));
        reservation.setEtudiants(new ArrayList<>());
    }



    @Test
     void testAjouterReservationEtAssignerAChambreEtAEtudiant() {
        // Simuler une chambre vide : aucune réservation pour cette chambre
        when(etudiantRepository.findByCin(12345L)).thenReturn(etudiant);
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);
        when(reservationRepository.getReservationsCurrentYear(any(), any(), eq(101L))).thenReturn(0); // chambre vide
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Appel de la méthode à tester
        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(reservation, 101L, 12345L);

        // Vérification des interactions et des résultats
        assertNotNull(result);
        assertEquals(1, result.getEtudiants().size());
        assertTrue(result.getEtudiants().contains(etudiant));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiantChambrePleine() {
        // Scénario : la chambre est pleine (2 réservations déjà en cours)

        when(etudiantRepository.findByCin(anyLong())).thenReturn(etudiant);
        when(chambreRepository.findByNumeroChambre(anyLong())).thenReturn(chambre);
        when(reservationRepository.getReservationsCurrentYear(any(), any(), anyLong())).thenReturn(2);

        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(reservation, 101L, 12345L);

        assertNull(result);  // La chambre est pleine, aucune réservation ne doit être ajoutée
        verify(reservationRepository, times(0)).save(any(Reservation.class)); // Aucun appel à save
    }


    @Test
    void testAjouterReservationChambrePleineWithError() {
        // Préparer le scénario où la chambre est pleine avec 2 réservations existantes
        chambre.setTypeC(TypeChambre.SIMPLE); // Assurez-vous que la chambre est du type 'SIMPLE'
        chambre.setReservations(List.of(new Reservation(), new Reservation())); // Ajouter deux réservations

        when(etudiantRepository.findByCin(12345L)).thenReturn(etudiant);
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre);
        when(reservationRepository.getReservationsCurrentYear(any(), any(), eq(101L))).thenReturn(2);  // Simulation du retour de 2 réservations

        // Appel de la méthode à tester avec une chambre pleine
        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(reservation, 101L, 12345L);

        // Vérification des résultats
        assertNull(result); // La méthode devrait retourner null si la chambre est pleine
        verify(reservationRepository, never()).save(any(Reservation.class)); // Vérifier que save n'a pas été appelé
        verify(chambreRepository, never()).save(chambre); // Vérifier que save pour la chambre n'a pas été appelé
    }






}
