package ms_cars.msstage.service;

import ms_cars.msstage.dto.requests.CreateCarRentalAgencyRequest;
import ms_cars.msstage.dto.requests.UpdateCarRentalRequest;
import ms_cars.msstage.dto.responses.ApiResponse;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.repository.CarRentalAgencyRepository;
import ms_cars.msstage.repository.GovernorateRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CarRentalAgencyService {

    private final CarRentalAgencyRepository agencyRepository;
    private final GovernorateRepository governorateRepository;
    private final FileStorageService fileStorageService;

    public CarRentalAgency createAgency(CreateCarRentalAgencyRequest data) throws RuntimeException {
        CarRentalAgency agency = new CarRentalAgency();
        agency.setName(data.getName().trim());
        agency.setAddress(data.getAddress().trim());
        Governorate g = governorateRepository.findById(data.getGovernorateId())
                .orElseThrow(() -> new RuntimeException("Governorate not found !"));

        agency.setGovernorate(g);
        if (!fileStorageService.fileExists(data.getFileName())) {
            throw new RuntimeException("FILE NOT FOUND!");
        }
        agency.setLogoImage(data.getFileName());

        return agencyRepository.save(agency);

    }

    public ApiResponse<List<CarRentalAgency>> getAllAgencies() {
        return ApiResponse.ok(agencyRepository.findAll());
    }

    public ApiResponse<CarRentalAgency> getAgencyById(UUID id) {
        return agencyRepository.findById(id)
                .map(agency -> ApiResponse.ok(agency))
                .orElse(ApiResponse.error("Agence non trouvée"));
    }

    public CarRentalAgency updateAgency(UpdateCarRentalRequest data) {

        // same as create , don't forget to test before updating values
        // in case user will update filename you should delete the old file from the
        // server

        CarRentalAgency old = agencyRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Agency not found !"));
        return old;
    }

    public void deleteAgency(UUID id) {
        if (agencyRepository.existsById(id)) {
            agencyRepository.findById(id).ifPresent(agency -> {
                if (agency.getLogoImage() != null) {
                    // deleteOldLogo(agency.getLogoImage());
                    // move this function to filestorage service
                }
            });

            agencyRepository.deleteById(id);

        }
    }

}