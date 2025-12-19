package ms_cars.msstage.service;

import jakarta.annotation.PostConstruct;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.repository.GovernorateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GovernorateService {

    private final GovernorateRepository governorateRepository;

    public GovernorateService(GovernorateRepository governorateRepository) {
        this.governorateRepository = governorateRepository;
    }


    @PostConstruct
    public void initGovernorates() {
        if (governorateRepository.count() == 0) {
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
                    "Zaghouan"
            );

            governorateNames.forEach(name -> {
                Governorate gov = new Governorate();
                gov.setName(name);
                governorateRepository.save(gov);
            });

            System.out.println("✅ 24 gouvernorats initialisés automatiquement !");
        }
    }


    public List<Governorate> getAllGovernorates() {
        return governorateRepository.findAll();
    }
}