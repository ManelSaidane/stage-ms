package ms_cars.msstage.service;

import jakarta.annotation.PostConstruct;
import ms_cars.msstage.entity.Airport;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.entity.Location;
import ms_cars.msstage.repository.GovernorateRepository;
import ms_cars.msstage.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final GovernorateRepository governorateRepository;

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    public LocationService(LocationRepository locationRepository, GovernorateRepository governorateRepository) {
        this.locationRepository = locationRepository;
        this.governorateRepository = governorateRepository;
    }

    @PostConstruct
    public void init() {
        logger.info("🔹 Initialisation des locations démarrée...");

        if (locationRepository.count() == 0) {

            logger.info("🟢 Table vide, ajout des gouvernorats...");

            List<Governorate> governorates = governorateRepository.findAll();
            for (Governorate gov : governorates) {
                Location loc = new Location();
                loc.setGovernorate(gov);
                loc.setFullCountry(false);
                loc.setAirport(null);
                locationRepository.save(loc);
                logger.info("✅ Gouvernorat ajouté: {}", gov.getName());
            }

            logger.info("🟢 Ajout des aéroports...");

            for (Airport airport : Airport.values()) {
                Location loc = new Location();
                loc.setAirport(airport);
                loc.setFullCountry(false);
                loc.setGovernorate(null);
                locationRepository.save(loc);
                logger.info("✅ Aéroport ajouté: {}", airport);
            }

            logger.info("🎉 Initialisation des locations terminée !");
        } else {
            logger.info("⚠️ Locations déjà présentes, rien à faire.");
        }
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
