package tn.esprit.foyer.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private String idReservation;
    private LocalDate anneeUniversitaire;
    private Boolean estValid;
}
