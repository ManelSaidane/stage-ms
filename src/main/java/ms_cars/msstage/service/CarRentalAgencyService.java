package ms_cars.msstage.service;

import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.repository.CarRentalAgencyRepository;
import ms_cars.msstage.repository.GovernorateRepository;
import ms_cars.msstage.shared.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarRentalAgencyService {

    private final CarRentalAgencyRepository agencyRepository;
    private final GovernorateRepository governorateRepository; // Nécessaire !

    private static final String UPLOAD_DIR = "CarAgency/";

    public CarRentalAgencyService(CarRentalAgencyRepository agencyRepository,
                                  GovernorateRepository governorateRepository) {
        this.agencyRepository = agencyRepository;
        this.governorateRepository = governorateRepository;
    }


    public ApiResponse<CarRentalAgency> createAgency(String name, String address,
                                                     List<UUID> governorateIds, MultipartFile logoFile) {
        try {
            CarRentalAgency agency = new CarRentalAgency();
            agency.setName(name.trim());
            agency.setAddress(address.trim());


            if (governorateIds != null && !governorateIds.isEmpty()) {
                Set<Governorate> governorates = new HashSet<>(governorateRepository.findAllById(governorateIds));
                if (governorates.size() != governorateIds.size()) {
                    return ApiResponse.error("Certains gouvernorats n'existent pas");
                }
                agency.setGovernorates(governorates);
            }


            if (logoFile != null && !logoFile.isEmpty()) {
                String fileName = saveLogoFile(logoFile, name);
                agency.setLogoImage(fileName);
            }

            CarRentalAgency saved = agencyRepository.save(agency);
            return ApiResponse.ok(saved, "Agence créée avec succès");
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la création : " + e.getMessage());
        }
    }


    public ApiResponse<List<CarRentalAgency>> getAllAgencies() {
        return ApiResponse.ok(agencyRepository.findAll());
    }


    public ApiResponse<CarRentalAgency> getAgencyById(UUID id) {
        return agencyRepository.findById(id)
                .map(agency -> ApiResponse.ok(agency))
                .orElse(ApiResponse.error("Agence non trouvée"));
    }


    public ApiResponse<CarRentalAgency> updateAgency(UUID id, String name, String address,
                                                     List<UUID> governorateIds, MultipartFile logoFile) {
        try {
            CarRentalAgency agency = agencyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Agence non trouvée"));

            if (name != null && !name.isBlank()) agency.setName(name.trim());
            if (address != null && !address.isBlank()) agency.setAddress(address.trim());

            if (governorateIds != null) {
                if (governorateIds.isEmpty()) {
                    agency.setGovernorates(new HashSet<>());
                } else {
                    Set<Governorate> governorates = new HashSet<>(governorateRepository.findAllById(governorateIds));
                    if (governorates.size() != governorateIds.size()) {
                        return ApiResponse.error("Certains gouvernorats n'existent pas");
                    }
                    agency.setGovernorates(governorates);
                }
            }

            if (logoFile != null && !logoFile.isEmpty()) {
                if (agency.getLogoImage() != null) {
                    deleteOldLogo(agency.getLogoImage());
                }
                String fileName = saveLogoFile(logoFile, agency.getName());
                agency.setLogoImage(fileName);
            }

            CarRentalAgency updated = agencyRepository.save(agency);
            return ApiResponse.ok(updated, "Agence mise à jour avec succès");
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }


    public ApiResponse<Void> deleteAgency(UUID id) {
        if (agencyRepository.existsById(id)) {
            agencyRepository.findById(id).ifPresent(agency -> {
                if (agency.getLogoImage() != null) {
                    deleteOldLogo(agency.getLogoImage());
                }
            });
            agencyRepository.deleteById(id);
            return ApiResponse.ok(null, "Agence supprimée avec succès");
        }
        return ApiResponse.error("Agence non trouvée");
    }

    private String saveLogoFile(MultipartFile file, String agencyName) throws IOException {
        String cleanName = (agencyName == null || agencyName.isBlank()) ? "unknown"
                : agencyName.replaceAll("[^a-zA-Z0-9_-]", "_").toLowerCase();

        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";

        String fileName = cleanName + "-" + UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    private void deleteOldLogo(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Impossible de supprimer l'ancien logo : " + fileName);
        }
    }
}