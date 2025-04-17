package tn.esprit.foyer.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.services.IReservationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/reservation")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ReservationRestController {
    IReservationService reservationService;
    // http://localhost:8089/foyer/reservation/retrieve-all-reservations
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






    // http://localhost:8089/foyer/reservation/retrieve-reservation/8
    @GetMapping("/retrieve-reservation/{reservationId}")
    @ResponseBody
    public Reservation retrieveReservation(@PathVariable("reservationId") Long reservationId) {
        return reservationService.retrieveReservation(reservationId);
    }

    // http://localhost:8089/foyer/reservation/add-reservation
    @PostMapping("/add-reservation")
    @ResponseBody
    public Reservation addReservation(@RequestBody Reservation r) {
        Reservation reservation= reservationService.addReservation(r);
        return reservation;
    }

    // http://localhost:8089/foyer/reservation/update-reservation
    @PutMapping("/update-reservation")
    @ResponseBody
    public Reservation updateReservation(@RequestBody Reservation r) {
        Reservation reservation= reservationService.updateReservation(r);
        return reservation;
    }
    // http://localhost:8089/foyer/reservation/removeReservation
    @DeleteMapping("/removeReservation/{idReservation}")
    @ResponseBody
    public void removeReservation(@PathVariable("idReservation") Long idReservation) {
        reservationService.removeReservation(idReservation);
    }

         // http://localhost:8089/foyer/reservation/ajouterReservationEtAssignerAChambreEtAEtudiant/15/8453621
         @PostMapping("/ajouterReservationEtAssignerAChambreEtAEtudiant/{numChambre}/{cin}")
         @ResponseBody
         public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(@RequestBody Reservation r,@PathVariable("numChambre") Long numChambre,@PathVariable("cin") long cin) {
             Reservation reservation= reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(r,numChambre,cin);
             return reservation;
         }
    // http://localhost:8089/foyer/reservation/getReservationParAnneeUniversitaire/2021-01-01/2021-12-31
    @GetMapping("/getReservationParAnneeUniversitaire/{dateDebut}/{dateFin}")
    @ResponseBody
    public List<Reservation> getReservationParAnneeUniversitaire(@PathVariable("dateDebut") LocalDate dateDebut,@PathVariable("dateFin") LocalDate dateFin) {
        return reservationService.getReservationParAnneeUniversitaire(dateDebut,dateFin);
    }
}
