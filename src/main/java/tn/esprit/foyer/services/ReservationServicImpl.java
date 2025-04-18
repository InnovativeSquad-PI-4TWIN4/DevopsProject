package tn.esprit.foyer.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.entities.TypeChambre;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.ReservationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationServicImpl implements IReservationService {

    ReservationRepository reservationRepository;
    ChambreRepository chambreRepository;
    EtudiantRepository etudiantRepository;


    @Override
    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }


       @Override
    public Reservation addReservation(Reservation r) {
        if (r.getEtudiants() == null) {
            r.setEtudiants(new ArrayList<>()); // éviter le NullPointerException
        }
        return reservationRepository.save(r);
    }


    @Override
    public Reservation updateReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    @Override
    public Reservation retrieveReservation(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public void removeReservation(Long idReservation){
            reservationRepository.deleteById(idReservation);

    }

    @Transactional
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Reservation res, Long numChambre, long cin) {
        // Dates de début et fin de l'année universitaire
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        // Récupération de l'étudiant et de la chambre
        Etudiant e = etudiantRepository.findByCin(cin);
        Chambre c = chambreRepository.findByNumeroChambre(numChambre);

        if (e == null || c == null) {
            log.warn("Étudiant ou chambre introuvable.");
            return null;
        }

        res.setEstValid(true);
        res.setAnneeUniversitaire(LocalDate.now());

        // Ajouter l'étudiant à la réservation
        List<Etudiant> etudiants = res.getEtudiants() != null ? new ArrayList<>(res.getEtudiants()) : new ArrayList<>();
        if (!etudiants.contains(e)) {
            etudiants.add(e);
        }
        res.setEtudiants(etudiants);

        // Compter les réservations existantes cette année pour cette chambre
        Integer reservationCount = reservationRepository.getReservationsCurrentYear(startDate, endDate, numChambre);
        TypeChambre type = c.getTypeC();

        boolean peutAjouter = false;

        switch (reservationCount) {
            case 0:
                peutAjouter = true;
                break;
            case 1:
                peutAjouter = (type == TypeChambre.DOUBLE || type == TypeChambre.TRIPLE);
                break;
            case 2:
                peutAjouter = (type == TypeChambre.TRIPLE);
                break;
            default:
                log.info("Capacité maximale atteinte pour la chambre " + numChambre);
                return null;
        }

        if (peutAjouter) {
            Reservation r = reservationRepository.save(res);
            List<Reservation> reservations = c.getReservations() != null ? new ArrayList<>(c.getReservations()) : new ArrayList<>();
            reservations.add(r);
            c.setReservations(reservations);
            chambreRepository.save(c);
            log.info("Réservation ajoutée avec succès pour la chambre " + numChambre);
            return r;
        } else {
            log.info("La chambre " + numChambre + " est déjà pleine.");
            return null;
        }
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaire(LocalDate dateDebut, LocalDate dateFin) {
        return reservationRepository.findByAnneeUniversitaireBetween(dateDebut,dateFin);
    }


}
