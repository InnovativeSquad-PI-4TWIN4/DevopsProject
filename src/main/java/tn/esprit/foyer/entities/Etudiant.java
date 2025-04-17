package tn.esprit.foyer.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // <- active le contrôle manuel
@ToString
public class Etudiant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant;

    @EqualsAndHashCode.Include
    private Long cin;

    private String nomEt;
    private String prenomEt;
    private String ecole;
    private LocalDate dateNaissance;

    @OneToMany(mappedBy = "etudiant")
    @JsonIgnore
    private List<Tache> taches;

    Float montantInscription;

    @Enumerated(EnumType.STRING)
    TypeEtudiant typeEtudiant;

    @ManyToMany(mappedBy = "etudiants", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Reservation> reservations;

    @OneToOne
    @JsonIgnore
    Tache tache;

    public Etudiant(String nomEt, String prenomEt, String ecole) {
        this.nomEt = nomEt;
        this.prenomEt = prenomEt;
        this.ecole = ecole;
    }
}
