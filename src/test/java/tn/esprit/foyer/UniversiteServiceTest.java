package tn.esprit.foyer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;
import tn.esprit.foyer.services.UniversiteServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock  // 🔹 Mock du Logger pour capturer les logs
    private Logger log;

    private UniversiteServiceImpl universiteService;

    private Foyer foyer;
    private Universite universite;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        universite = new Universite();
        universiteService = new UniversiteServiceImpl(universiteRepository, foyerRepository, log);  // ✅ Injecte le logger mocké
    }

    @Test
    void testAffecterFoyerAUniversite() {
        long idFoyer = 1L;
        String nomUniversite = "Université Test";

        when(foyerRepository.findById(idFoyer)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite(nomUniversite)).thenReturn(universite);

        Universite result = universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);

        assertNotNull(result);
        assertEquals(universite, foyer.getUniversite());

        verify(foyerRepository).save(foyer);
        verify(log, times(1)).info("Fin méthode affecterFoyerAUniversite");  // ✅ Vérification du log
    }
}
