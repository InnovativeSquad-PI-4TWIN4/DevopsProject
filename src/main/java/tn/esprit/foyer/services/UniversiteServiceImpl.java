package tn.esprit.foyer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;
import java.util.List;

@Service
public class UniversiteServiceImpl implements IUniversiteService {
    private final UniversiteRepository universiteRepository;
    private final FoyerRepository foyerRepository;
    private final Logger log;

    // 🔹 Constructeur modifié pour accepter un Logger injecté
    public UniversiteServiceImpl(UniversiteRepository universiteRepository, FoyerRepository foyerRepository, Logger log) {
        this.universiteRepository = universiteRepository;
        this.foyerRepository = foyerRepository;
        this.log = log;  // Utilisation du Logger injecté
    }

    @Override
    public List<Universite> retrieveAllUniversites() {
        log.info("Début méthode retrieveAllUniversites");
        return universiteRepository.findAll();
    }

    @Override
    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversite(Universite u) {
        log.info("Début méthode updateUniversite");
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversite(Long idUniversite) {
        return universiteRepository.findById(idUniversite).orElse(null);
    }

    @Override
    public void removeUniversite(Long idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer f = foyerRepository.findById(idFoyer).orElseThrow(() -> new RuntimeException("Foyer non trouvé"));
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite);
        f.setUniversite(universite);
        foyerRepository.save(f);
        log.info("Fin méthode affecterFoyerAUniversite");
        return universite;
    }

    @Override
    public Long desaffecterFoyerAUniversite(long idFoyer) {
        Foyer f = foyerRepository.findById(idFoyer).orElseThrow(() -> new RuntimeException("Foyer non trouvé"));
        f.setUniversite(null);
        foyerRepository.save(f);
        return f.getIdFoyer();
    }
}
