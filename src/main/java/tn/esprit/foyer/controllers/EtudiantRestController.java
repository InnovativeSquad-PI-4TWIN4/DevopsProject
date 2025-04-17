package tn.esprit.foyer.controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.services.EtudiantServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Gestion des étudiants")
public class EtudiantRestController {

    EtudiantServiceImpl etudiantService;

    @GetMapping("/retrieve-all-etudiants")
    @Operation(description = "récupérer la liste des étudiants")
    public List<Etudiant> getEtudiants() {
        return etudiantService.retrieveAllEtudiants();
    }

    @GetMapping("/retrieve-etudiant/{etudiantId}")
    @Operation(description = "récupérer un étudiant par son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Etudiant",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Etudiant.class))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content)
    })
    public Etudiant retrieveEtudiant(
            @Parameter(description = "id of student to be searched")
            @PathVariable("etudiantId") Long etudiantId) {
        return etudiantService.retrieveEtudiant(etudiantId);
    }

    @PostMapping("/add-etudiant")
    @Operation(description = "ajouter un étudiant")
    public Etudiant addEtudiant(@RequestBody Etudiant e) {
        return etudiantService.addEtudiant(e);
    }

    @PutMapping("/update-etudiant")
    @Operation(description = "modifier un étudiant")
    public Etudiant updateEtudiant(@RequestBody Etudiant e) {
        return etudiantService.updateEtudiant(e);
    }

    @DeleteMapping("/removeEtudiant/{idEtudiant}")
    public void removeEtudiant(@PathVariable("idEtudiant") Long idEtudiant) {
        etudiantService.removeEtudiant(idEtudiant);
    }

    @PostMapping("/add-etudiants")
    @Operation(description = "ajouter une liste étudiants")
    public List<Etudiant> addEtudiants(@RequestBody List<Etudiant> etudiants) {
        return etudiantService.addEtudiants(etudiants);
    }

    @PutMapping("/affecterEtudiantAReservation/{nomEt}/{prenomEt}/{idReservation}")
    @Operation(description = "assigner un étudiant à une résérvation")
    public Etudiant affecterEtudiantAReservation(
            @PathVariable("nomEt") String nomEt,
            @PathVariable("prenomEt") String prenomEt,
            @PathVariable("idReservation") String idReservation) {
        return etudiantService.affecterEtudiantAReservation(nomEt, prenomEt, idReservation);
    }
}
