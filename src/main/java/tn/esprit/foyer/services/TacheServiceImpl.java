package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TacheServiceImpl implements ITacheService {

    TacheRepository tacheRepository;
    EtudiantRepository etudiantRepository;

    @Override
    public List<Tache> retrieveAllTaches() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache addTache(Tache t) {
        return tacheRepository.save(t);
    }

    @Override
    public Tache updateTache(Tache t) {
        return tacheRepository.save(t);
    }

    @Override
    public Tache retrieveTache(Long idTache) {
        return tacheRepository.findById(idTache)
                .orElseThrow(() -> new IllegalArgumentException("Tâche non trouvée avec l'ID : " + idTache));
    }

    @Override
    public void removeTache(Long idTache) {
        tacheRepository.deleteById(idTache);
    }

    @Override
    public List<Tache> addTachesAndAffectToEtudiant(List<Tache> taches, String nomEt, String prenomEt) {
        Etudiant et = etudiantRepository.findByNomEtAndPrenomEt(nomEt, prenomEt);
        taches.forEach(t -> t.setEtudiant(et));
        return tacheRepository.saveAll(taches);
    }

    @Override
    public HashMap<String, Float> calculNouveauMontantInscriptionDesEtudiants() {
        HashMap<String, Float> nouveauxMontants = new HashMap<>();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        etudiantRepository.findAll().forEach(etudiant -> {
            Float montantInitial = etudiant.getMontantInscription();
            Float reduction = tacheRepository.sommeTacheAnneeEncours(startDate, endDate, etudiant.getIdEtudiant());
            Float montantFinal = reduction != null ? montantInitial - reduction : montantInitial;
            nouveauxMontants.put(etudiant.getNomEt() + " " + etudiant.getPrenomEt(), montantFinal);
        });

        return nouveauxMontants;
    }

    public void updateNouveauMontantInscriptionDesEtudiants() {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);

        etudiantRepository.findAll().forEach(etudiant -> {
            Float reduction = tacheRepository.sommeTacheAnneeEncours(startDate, endDate, etudiant.getIdEtudiant());
            if (reduction != null) {
                etudiant.setMontantInscription(etudiant.getMontantInscription() - reduction);
                etudiantRepository.save(etudiant);
            }
        });
    }
}
