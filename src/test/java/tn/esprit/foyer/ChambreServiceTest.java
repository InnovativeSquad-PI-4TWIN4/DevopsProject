package tn.esprit.foyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.foyer.entities.*;
import tn.esprit.foyer.repository.BlocRepository;
import tn.esprit.foyer.repository.ChambreRepository;
import tn.esprit.foyer.repository.FoyerRepository;
import tn.esprit.foyer.services.ChambreServiceImpl;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // JUnit + Mockito
public class ChambreServiceTest {

    @Mock  // Mockito : simulateur de ChambreRepository
    private ChambreRepository chambreRepository;

    @Mock  // Mockito : simulateur de BlocRepository
    private BlocRepository blocRepository;

    @Mock  // Mockito : simulateur de FoyerRepository
    private FoyerRepository foyerRepository;

    @InjectMocks // Injecte automatiquement les mocks dans le service
    private ChambreServiceImpl chambreService;

    private Chambre chambre1, chambre2;
    private Foyer foyer;
    private Bloc bloc;

    @BeforeEach // JUnit : exécuté avant chaque test
    void setUp() {
        chambre1 = new Chambre();
        chambre1.setIdChambre(1L);
        chambre1.setNumeroChambre(101L);
        chambre1.setTypeC(TypeChambre.DOUBLE);

        chambre2 = new Chambre();
        chambre2.setIdChambre(2L);
        chambre2.setNumeroChambre(102L);
        chambre2.setTypeC(TypeChambre.SIMPLE);

        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setChambres(List.of(chambre1, chambre2));

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer Centrale");
        foyer.setBlocs(List.of(bloc));
    }

    @Test // JUnit : test unitaire de logique métier
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() {
        // Mockito : définir le comportement simulé
        when(foyerRepository.findByNomFoyer("Foyer Centrale")).thenReturn(foyer);
        when(chambreRepository.checkNbReservationsChambre(any(), any(), eq(TypeChambre.DOUBLE), eq(101L)))
                .thenReturn(1L); // chambre disponible car < 2

        // Appel de la méthode testée
        List<Chambre> result = chambreService.getChambresNonReserveParNomFoyerEtTypeChambre("Foyer Centrale", TypeChambre.DOUBLE);

        // Vérification JUnit
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getNumeroChambre());
        assertEquals(TypeChambre.DOUBLE, result.get(0).getTypeC());
    }

    @Test // JUnit
    void testNbChambreParTypeEtBloc() {
        when(chambreRepository.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L)).thenReturn(4L);

        long count = chambreService.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L);

        assertEquals(4L, count);
        verify(chambreRepository, times(1)).nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L);
    }

    @Test // JUnit
    void testPourcentageChambreParTypeChambre() {
        // Mockito
        List<Chambre> chambres = Arrays.asList(
                createChambre(TypeChambre.SIMPLE),
                createChambre(TypeChambre.SIMPLE),
                createChambre(TypeChambre.DOUBLE),
                createChambre(TypeChambre.TRIPLE)
        );

        when(chambreRepository.findAll()).thenReturn(chambres);
        when(chambreRepository.nbChambresParType(TypeChambre.SIMPLE)).thenReturn(2);
        when(chambreRepository.nbChambresParType(TypeChambre.DOUBLE)).thenReturn(1);
        when(chambreRepository.nbChambresParType(TypeChambre.TRIPLE)).thenReturn(1);

        // Appel de la méthode métier
        chambreService.pourcentageChambreParTypeChambre();

        // Mockito : vérification
        verify(chambreRepository).nbChambresParType(TypeChambre.SIMPLE);
        verify(chambreRepository).nbChambresParType(TypeChambre.DOUBLE);
        verify(chambreRepository).nbChambresParType(TypeChambre.TRIPLE);
    }

    // Méthode utilitaire
    private Chambre createChambre(TypeChambre type) {
        Chambre c = new Chambre();
        c.setTypeC(type);
        return c;
    }
}
