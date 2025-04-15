package tn.esprit.foyer.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.entities.Universite;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.repository.UniversiteRepository;
import tn.esprit.foyer.services.UniversiteServiceImpl;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UniversiteTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("ESPRIT");

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
    }

    @Test
    void testRetrieveAllUniversites() {
        when(universiteRepository.findAll()).thenReturn(List.of(universite));

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertEquals(1, result.size());
        assertEquals("ESPRIT", result.get(0).getNomUniversite());
    }

    @Test
    void testAddUniversite() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertNotNull(result);
        assertEquals("ESPRIT", result.getNomUniversite());
    }

    @Test
    void testUpdateUniversite() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.updateUniversite(universite);

        assertNotNull(result);
        assertEquals("ESPRIT", result.getNomUniversite());
    }

    @Test
    void testRetrieveUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdUniversite());
    }

    @Test
    void testRemoveUniversite() {
        doNothing().when(universiteRepository).deleteById(1L);

        assertDoesNotThrow(() -> universiteService.removeUniversite(1L));
    }

    @Test
    void testAffecterFoyerAUniversite() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite("ESPRIT")).thenReturn(universite);
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Universite result = universiteService.affecterFoyerAUniversite(1L, "ESPRIT");

        assertNotNull(result);
        assertEquals("ESPRIT", result.getNomUniversite());
        assertEquals(universite, foyer.getUniversite());
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        foyer.setUniversite(universite);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Long result = universiteService.desaffecterFoyerAUniversite(1L);

        assertEquals(1L, result);
        assertNull(foyer.getUniversite());
    }
}
