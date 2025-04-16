package tn.esprit.foyer;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import tn.esprit.foyer.entities.Etudiant;
        import tn.esprit.foyer.entities.Reservation;

        import java.time.LocalDate;
        import java.util.Collections;
        import java.util.List;

        import static org.junit.jupiter.api.Assertions.*;

public class TestReservationServiceImpl {

    private Reservation reservation;
    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setNomEt("Ali");
        etudiant.setPrenomEt("Ben Salah");
        etudiant.setCin(12345678L);

        reservation = Reservation.builder()
                .idReservation("RES123")
                .anneeUniversitaire(LocalDate.of(2023, 9, 1))
                .estValid(true)
                .etudiants(Collections.singletonList(etudiant))
                .build();
    }

    @Test
    void testReservationFields() {
        assertEquals("RES123", reservation.getIdReservation());
        assertEquals(LocalDate.of(2023, 9, 1), reservation.getAnneeUniversitaire());
        assertTrue(reservation.getEstValid());
        assertNotNull(reservation.getEtudiants());
        assertEquals(1, reservation.getEtudiants().size());
        assertEquals("Ali", reservation.getEtudiants().get(0).getNomEt());
    }

    @Test
    void testSettersAndGetters() {
        reservation.setIdReservation("RES999");
        reservation.setAnneeUniversitaire(LocalDate.of(2024, 9, 1));
        reservation.setEstValid(false);
        reservation.setEtudiants(List.of());

        assertEquals("RES999", reservation.getIdReservation());
        assertEquals(LocalDate.of(2024, 9, 1), reservation.getAnneeUniversitaire());
        assertFalse(reservation.getEstValid());
        assertTrue(reservation.getEtudiants().isEmpty());
    }
}
