package tn.esprit.foyer;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.foyer.controllers.ReservationRestController;
import tn.esprit.foyer.entities.Reservation;
import tn.esprit.foyer.services.IReservationService;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRetrieveAllReservations() throws Exception {
        Mockito.when(reservationService.retrieveAllReservations())
                .thenReturn(Collections.singletonList(new Reservation()));
        mockMvc.perform(get("/reservation/retrieve-all-reservations"))
                .andExpect(status().isOk());
    }

    @Test
    void testRetrieveReservationById() throws Exception {
        Mockito.when(reservationService.retrieveReservation(1L))
                .thenReturn(new Reservation());
        mockMvc.perform(get("/reservation/retrieve-reservation/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddReservation() throws Exception {
        Reservation r = new Reservation();
        Mockito.when(reservationService.addReservation(any(Reservation.class)))
                .thenReturn(r);
        mockMvc.perform(post("/reservation/add-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateReservation() throws Exception {
        Reservation r = new Reservation();
        Mockito.when(reservationService.updateReservation(any(Reservation.class)))
                .thenReturn(r);
        mockMvc.perform(put("/reservation/update-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk());
    }

    @Test
    void testRemoveReservation() throws Exception {
        Mockito.doNothing().when(reservationService).removeReservation(1L);
        mockMvc.perform(delete("/reservation/removeReservation/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant() throws Exception {
        Reservation r = new Reservation();
        Mockito.when(reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(any(), eq(15L), eq(8453621L)))
                .thenReturn(r);
        mockMvc.perform(post("/reservation/ajouterReservationEtAssignerAChambreEtAEtudiant/15/8453621")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReservationParAnneeUniversitaire() throws Exception {
        Mockito.when(reservationService.getReservationParAnneeUniversitaire(any(), any()))
                .thenReturn(Arrays.asList(new Reservation(), new Reservation()));
        mockMvc.perform(get("/reservation/getReservationParAnneeUniversitaire/2023-01-01/2023-12-31"))
                .andExpect(status().isOk());
    }
}
