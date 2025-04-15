package tn.esprit.foyer.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.Etudiant;
import tn.esprit.foyer.entities.Tache;
import tn.esprit.foyer.repository.EtudiantRepository;
import tn.esprit.foyer.repository.TacheRepository;
import tn.esprit.foyer.services.TacheServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TacheTestMockito {

    @Mock   //Permet de créer un mock (simulation) d'une dépendance.
    private TacheRepository tacheRepository;

    @Mock  //mockitho
    private EtudiantRepository etudiantRepository;

    @InjectMocks    //mockitho   Injecte automatiquement les mocks marqués avec @Mock dans l'objet testé.
    private TacheServiceImpl tacheService;

    private Etudiant etudiant;
    private Tache tache1, tache2;

    @BeforeEach   //JUnit   Indique qu'une méthode doit être exécutée avant chaque test (@Test).
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEt("Doe");
        etudiant.setPrenomEt("John");
        etudiant.setMontantInscription(1000f);

        tache1 = new Tache();
        tache1.setIdTache(1L);


        tache2 = new Tache();
        tache2.setIdTache(2L);

    }

    @Test   //JUnit    un test unitaire.
    void testRetrieveAllTaches() {
        when(tacheRepository.findAll()).thenReturn(Arrays.asList(tache1, tache2));
        List<Tache> taches = tacheService.retrieveAllTaches();
        assertEquals(2, taches.size());
        verify(tacheRepository, times(1)).findAll();
    }

    @Test
    void testAddTache() {
        when(tacheRepository.save(any(Tache.class))).thenReturn(tache1);
        Tache savedTache = tacheService.addTache(tache1);
        assertNotNull(savedTache);
        assertEquals(tache1.getIdTache(), savedTache.getIdTache());
        verify(tacheRepository, times(1)).save(tache1);
    }

    @Test
    void testRetrieveTache() {
        when(tacheRepository.findById(1L)).thenReturn(Optional.of(tache1));
        Tache foundTache = tacheService.retrieveTache(1L);
        assertNotNull(foundTache);
        assertEquals(1L, foundTache.getIdTache());
        verify(tacheRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveTache() {
        doNothing().when(tacheRepository).deleteById(1L);
        tacheService.removeTache(1L);
        verify(tacheRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddTachesAndAffectToEtudiant() {
        when(etudiantRepository.findByNomEtAndPrenomEt("Doe", "John")).thenReturn(etudiant);
        when(tacheRepository.saveAll(anyList())).thenReturn(Arrays.asList(tache1, tache2));

        List<Tache> savedTaches = tacheService.addTachesAndAffectToEtudiant(Arrays.asList(tache1, tache2), "Doe", "John");

        assertEquals(2, savedTaches.size());
        verify(tacheRepository, times(1)).saveAll(anyList());
    }

    @Test  //JUnit    un test unitaire.
    void testCalculNouveauMontantInscriptionDesEtudiants() {
        when(etudiantRepository.findAll()).thenReturn(List.of(etudiant));   //mockito est utilisé pour définir le comportement d'un mock (objet simulé).
        when(tacheRepository.sommeTacheAnneeEncours(any(LocalDate.class), any(LocalDate.class), anyLong())).thenReturn(300f);

        HashMap<String, Float> result = tacheService.calculNouveauMontantInscriptionDesEtudiants();

        assertEquals(1, result.size());  //JUnit   egale ou non
        assertEquals(700f, result.get("Doe John"));   //JUnit
        verify(etudiantRepository, times(1)).findAll();   //Mockito  vérifier si une méthode a été appelée sur un mock (objet simulé)
        verify(tacheRepository, times(1)).sommeTacheAnneeEncours(any(), any(), anyLong());   //mockitho
    }
}
