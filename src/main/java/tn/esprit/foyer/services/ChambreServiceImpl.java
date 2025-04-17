package tn.esprit.foyer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class ChambreServiceImpl  {
/*
    ChambreRepository chambreRepository;
    BlocRepository blocRepository;
    FoyerRepository foyerRepository;

    @Override
    public List<Chambre> retrieveAllChambres() {
        log.info("▶️ In method retrieveAllChambres()");
        return chambreRepository.findAll();
    }

    @Override
    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre updateChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    @Override
    public Chambre retrieveChambre(Long idChambre) {
        return chambreRepository.findById(idChambre)
                .orElseThrow(() -> new IllegalArgumentException("Chambre introuvable avec l'ID : " + idChambre));
    }

    @Override
    public void removeChambre(Long idChambre) {
        chambreRepository.deleteById(idChambre);
    }

    @Override
    public List<Chambre> findByTypeCAndBlocIdBloc(TypeChambre typeChambre, Long idBloc) {
        return chambreRepository.findByTypeCAndBlocIdBloc(typeChambre, idBloc);
    }

    @Override
    public List<Chambre> findByReservationsEstValid(Boolean estValid) {
        return chambreRepository.findByReservationsValide(estValid);
    }

    @Override
    public List<Chambre> findByBlocIdBlocAndBlocCapaciteBlocGreaterThan(Long idBloc, Long capaciteBloc) {
        return chambreRepository.findByBlocIdBlocAndBlocCapaciteBloc(idBloc, capaciteBloc);
    }

    @Override
    public List<Chambre> getChambresParNomBloc(String nomBloc) {
        return chambreRepository.findByBlocNomBloc(nomBloc);
    }

    @Override
    public long nbChambreParTypeEtBloc(TypeChambre type, long idBloc) {
        return chambreRepository.nbChambreParTypeEtBloc(type, idBloc);
    }

    @Override
    public List<Chambre> getChambresNonReserveParNomFoyerEtTypeChambre(String nomFoyer, TypeChambre type) {
        List<Chambre> chambresDisponibles = new ArrayList<>();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate endDate = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        Foyer foyer = foyerRepository.findByNomFoyer(nomFoyer);

        if (foyer != null && foyer.getBlocs() != null) {
            foyer.getBlocs().forEach(bloc ->
                    bloc.getChambres().forEach(chambre -> {
                        if (chambre.getTypeC().equals(type)) {
                            Long nbRes = chambreRepository.checkNbReservationsChambre(startDate, endDate, type, chambre.getNumeroChambre());
                            if ((type == TypeChambre.SIMPLE && nbRes == 0) ||
                                    (type == TypeChambre.DOUBLE && nbRes < 2) ||
                                    (type == TypeChambre.TRIPLE && nbRes < 3)) {
                                chambresDisponibles.add(chambre);
                            }
                        }
                    })
            );
        }

        return chambresDisponibles;
    }

    // @Scheduled(fixedRate = 60000)
    public void pourcentageChambreParTypeChambre() {
        int nbTotal = chambreRepository.findAll().size();
        log.info("Nombre total de chambres : {}", nbTotal);

        Arrays.stream(TypeChambre.values()).forEach(type -> {
            int nbParType = chambreRepository.nbChambresParType(type);
            double pourcentage = (nbParType * 100.0) / nbTotal;
            log.info("📊 Pourcentage de chambres pour le type {} : {}%", type, pourcentage);
        });
    }*/
}
