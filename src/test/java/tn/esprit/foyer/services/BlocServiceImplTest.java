package tn.esprit.foyer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Bloc;
import tn.esprit.foyer.entities.Chambre;
import tn.esprit.foyer.entities.Foyer;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc testBloc;
    private List<Bloc> testBlocs;

    @BeforeEach
    public void setUp() {
        // More comprehensive test data setup
        testBloc = new Bloc();
        testBloc.setIdBloc(1L);
        testBloc.setNomBloc("Test Bloc");
        testBloc.setCapaciteBloc(100L);

        Foyer testFoyer = new Foyer();
        testFoyer.setIdFoyer(1L);
        testBloc.setFoyer(testFoyer);

        testBlocs = new ArrayList<>();
        testBlocs.add(testBloc);
    }

    @Test
    public void testRetrieveAllBlocs_NonEmptyList() {
        when(blocRepository.findAll()).thenReturn(testBlocs);

        List<Bloc> retrievedBlocs = blocService.retrieveAllBlocs();

        assertNotNull(retrievedBlocs);
        assertEquals(1, retrievedBlocs.size());
        verify(blocRepository).findAll();
    }

    @Test
    public void testRetrieveAllBlocs_EmptyList() {
        when(blocRepository.findAll()).thenReturn(new ArrayList<>());

        List<Bloc> retrievedBlocs = blocService.retrieveAllBlocs();

        assertTrue(retrievedBlocs.isEmpty());
        verify(blocRepository).findAll();
    }

    @Test
    public void testAddBloc_ValidBloc() {
        when(blocRepository.save(testBloc)).thenReturn(testBloc);

        Bloc addedBloc = blocService.addBloc(testBloc);

        assertNotNull(addedBloc);
        assertEquals(testBloc.getIdBloc(), addedBloc.getIdBloc());
        verify(blocRepository).save(testBloc);
    }

    @Test
    public void testUpdateBloc_ExistingBloc() {
        Bloc updatedBloc = new Bloc();
        updatedBloc.setIdBloc(1L);
        updatedBloc.setNomBloc("Updated Bloc Name");
        updatedBloc.setCapaciteBloc(150L);

        when(blocRepository.save(updatedBloc)).thenReturn(updatedBloc);

        Bloc result = blocService.updateBloc(updatedBloc);

        assertNotNull(result);
        assertEquals("Updated Bloc Name", result.getNomBloc());
        assertEquals(150L, result.getCapaciteBloc());
    }

    @Test
    public void testRetrieveBloc_Exists() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(testBloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        assertNotNull(retrievedBloc);
        assertEquals(testBloc.getIdBloc(), retrievedBloc.getIdBloc());
    }

    @Test
    public void testRetrieveBloc_NotExists() {
        when(blocRepository.findById(99L)).thenReturn(Optional.empty());

        Bloc retrievedBloc = blocService.retrieveBloc(99L);

        assertNull(retrievedBloc);
    }

    @Test
    public void testRemoveBloc_Successful() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.removeBloc(1L);

        verify(blocRepository).deleteById(1L);
    }

    @Test
    public void testFindByFoyerUniversite_WithResults() {
        Long universite = 1L;
        when(blocRepository.findByFoyerUniversite(universite)).thenReturn(testBlocs);

        List<Bloc> result = blocService.findByFoyerUniversiteIdUniversite(universite);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    public void testAffecterChambresABloc_MultipleChambers() {
        String nomBloc = "Test Bloc";
        List<Long> numChambre = Arrays.asList(101L, 102L);

        when(blocRepository.findByNomBloc(nomBloc)).thenReturn(testBloc);

        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);

        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre1);
        when(chambreRepository.findByNumeroChambre(102L)).thenReturn(chambre2);

        Bloc resultBloc = blocService.affecterChambresABloc(numChambre, nomBloc);

        assertNotNull(resultBloc);
        verify(blocRepository).findByNomBloc(nomBloc);
        verify(chambreRepository, times(2)).findByNumeroChambre(anyLong());
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

    @Test
    public void testScheduledMethods() {
        // For methods with @Scheduled annotation
        blocService.fixedRateMethod();
        blocService.listeChambresParBloc();
        // These are mostly for coverage of logging methods
    }
}