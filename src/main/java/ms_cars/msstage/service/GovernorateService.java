package ms_cars.msstage.service;

import jakarta.annotation.PostConstruct;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.repository.GovernorateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GovernorateService {
    List<String> governorateNames = List.of(
            "Ariana",
            "Béja",
            "Ben Arous",
            "Bizerte",
            "Gabès",
            "Gafsa",
            "Jendouba",
            "Kairouan",
            "Kasserine",
            "Kébili",
            "Le Kef",
            "Mahdia",
            "La Manouba",
            "Médenine",
            "Monastir",
            "Nabeul",
            "Sfax",
            "Sidi Bouzid",
            "Siliana",
            "Sousse",
            "Tataouine",
            "Tozeur",
            "Tunis",
            "Zaghouan");
    private final GovernorateRepository governorateRepository;

    public GovernorateService(GovernorateRepository governorateRepository) {
        this.governorateRepository = governorateRepository;
    }

    @PostConstruct
    public void init() {
        if (governorateRepository.count() != 24) {
            governorateRepository.deleteAll();

            governorateNames.forEach(name -> {
                Governorate gov = new Governorate(name);

                governorateRepository.save(gov);
            });

            System.out.println("✅ 24 gouvernorats initialisés automatiquement !");
        }
    }

    public List<Governorate> getAllGovernorates() {
        return governorateRepository.findAll();
    }

}