package tn.esprit.foyer.entities;


import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor

public class Etudiant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant; // Clé primaire
    @NonNull
    private String nomEt;
    @NonNull
     String prenomEt;
     Long cin;
     String ecole;
     LocalDate dateNaissance;










    @OneToMany(mappedBy = "etudiant")
    @JsonIgnore
    List<Tache> taches;
    Float montantInscription;
    @Enumerated(EnumType.STRING)
    TypeEtudiant typeEtudiant;
    @JsonIgnore
    @ManyToMany(mappedBy = "etudiants",fetch = FetchType.EAGER)
    List<Reservation> reservations;

    @OneToOne
    @JsonIgnore
    Tache tache;
    public Etudiant(String nomEt, String prenomEt, String ecole) {
        this.nomEt = nomEt;
        this.prenomEt = prenomEt;
        this.ecole = ecole;
    }





}

