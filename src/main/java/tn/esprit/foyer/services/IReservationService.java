package tn.esprit.foyer.services;

import tn.esprit.foyer.entities.Reservation;
import java.time.LocalDate;
import java.util.List;

public interface IReservationService {

    List<Reservation> retrieveAllReservations();
    Reservation addReservation(Reservation r);
    Reservation updateReservation(Reservation r);
    Reservation retrieveReservation(Long idReservation);
    void removeReservation(Long idReservation);

    Reservation ajouterReservationEtAssignerAChambreEtAEtudiant (Reservation res, Long
            numChambre, long cin) ;

    List<Reservation> getReservationParAnneeUniversitaire(LocalDate dateDebut, LocalDate dateFin) ;

}
