package tn.esprit.foyer.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.services.IReservationService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/reservation")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class ReservationRestController {

    IReservationService reservationService;

    @GetMapping("/retrieve-all-reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> list = reservationService.retrieveAllReservations();
            log.info("✅ Total réservations retournées : {}", list.size());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des réservations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/retrieve-reservation/{reservationId}")
    public Reservation retrieveReservation(@PathVariable("reservationId") Long reservationId) {
        return reservationService.retrieveReservation(reservationId);
    }

    @PostMapping("/add-reservation")
    public Reservation addReservation(@RequestBody Reservation r) {
        return reservationService.addReservation(r);
    }

    @PutMapping("/update-reservation")
    public Reservation updateReservation(@RequestBody Reservation r) {
        return reservationService.updateReservation(r);
    }

    @DeleteMapping("/removeReservation/{idReservation}")
    public void removeReservation(@PathVariable("idReservation") Long idReservation) {
        reservationService.removeReservation(idReservation);
    }

    @PostMapping("/ajouterReservationEtAssignerAChambreEtAEtudiant/{numChambre}/{cin}")
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(
            @RequestBody Reservation r,
            @PathVariable("numChambre") Long numChambre,
            @PathVariable("cin") long cin
    ) {
        return reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(r, numChambre, cin);
    }

    @GetMapping("/getReservationParAnneeUniversitaire/{dateDebut}/{dateFin}")
    public List<Reservation> getReservationParAnneeUniversitaire(
            @PathVariable("dateDebut") LocalDate dateDebut,
            @PathVariable("dateFin") LocalDate dateFin
    ) {
        return reservationService.getReservationParAnneeUniversitaire(dateDebut, dateFin);
    }
}
