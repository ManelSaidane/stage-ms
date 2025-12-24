package ms_cars.msstage.service;

import ms_cars.msstage.dto.requests.CreateCarRentalAgencyRequest;
import ms_cars.msstage.dto.requests.UpdateCarRentalRequest;
import ms_cars.msstage.dto.responses.ApiResponse;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.repository.CarRentalAgencyRepository;
import ms_cars.msstage.repository.GovernorateRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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


    public ApiResponse<Page<CarRentalAgency>> getAllAgencies(int page, int size) {
        Page<CarRentalAgency> agenciesPage = agencyRepository.findAll(PageRequest.of(page, size));
        return ApiResponse.ok(agenciesPage, "Agencies fetched successfully");
    }    public ApiResponse<List<CarRentalAgency>> getAllAgencies() {
        return ApiResponse.ok(agencyRepository.findAll());
    }

    public ApiResponse<CarRentalAgency> getAgencyById(UUID id) {
        return agencyRepository.findById(id)
                .map(agency -> ApiResponse.ok(agency))
                .orElse(ApiResponse.error("Agence non trouvée"));
    }

    public CarRentalAgency updateAgency(UpdateCarRentalRequest data) {

        CarRentalAgency agency = agencyRepository.findById(data.getId())
                .orElseThrow(() -> new RuntimeException("Agency not found !"));


        if (data.getName() != null && !data.getName().trim().isEmpty()) {
            agency.setName(data.getName().trim());
        }

        if (data.getAddress() != null && !data.getAddress().trim().isEmpty()) {
            agency.setAddress(data.getAddress().trim());
        }


        if (data.getGovernorateId() != null) {
            Governorate governorate = governorateRepository.findById(data.getGovernorateId())
                    .orElseThrow(() -> new RuntimeException("Governorate not found !"));
            agency.setGovernorate(governorate);
        }


        if (data.getFileName() != null && !data.getFileName().trim().isEmpty()) {
            String newFileName = data.getFileName().trim();


            if (!fileStorageService.fileExists(newFileName)) {
                throw new RuntimeException("Nouveau fichier logo introuvable : " + newFileName);
            }


            String oldLogo = agency.getLogoImage();
            if (oldLogo != null && !oldLogo.equals(newFileName)) {
                try {
                    fileStorageService.deleteFile(oldLogo);
                } catch (Exception e) {

                    System.err.println("Impossible de supprimer l'ancien logo : " + oldLogo);

                }
            }


            agency.setLogoImage(newFileName);
        }


        return agencyRepository.save(agency);
    }

    public void deleteAgency(UUID id) {

        CarRentalAgency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agency not found !"));


        if (agency.getLogoImage() != null) {
            try {
                fileStorageService.deleteFile(agency.getLogoImage());
            } catch (Exception e) {

                System.err.println("Impossible de supprimer le logo : " + agency.getLogoImage());

            }
        }


        agencyRepository.delete(agency);

    }

    // Service
    public Page<CarRentalAgency> byName(String name, int page, int size) {
        List<CarRentalAgency> all = agencyRepository.findAll();
        List<CarRentalAgency> filtered = all.stream()
                .filter(a -> a.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        return new PageImpl<>(filtered.subList(start, end), PageRequest.of(page, size), filtered.size());
    }

    public Page<CarRentalAgency> byAddr(String addr, int page, int size) {
        List<CarRentalAgency> all = agencyRepository.findAll();
        List<CarRentalAgency> filtered = all.stream()
                .filter(a -> a.getAddress().toLowerCase().contains(addr.toLowerCase()))
                .toList();

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        return new PageImpl<>(filtered.subList(start, end), PageRequest.of(page, size), filtered.size());
    }

    public Page<CarRentalAgency> byGov(String gov, int page, int size) {
        List<CarRentalAgency> all = agencyRepository.findAll();
        List<CarRentalAgency> filtered = all.stream()
                .filter(a -> a.getGovernorate().getName().toLowerCase().contains(gov.toLowerCase()))
                .toList();

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        return new PageImpl<>(filtered.subList(start, end), PageRequest.of(page, size), filtered.size());
    }

    public Page<CarRentalAgency> filter(String name, String addr, String gov, int page, int size) {
        List<CarRentalAgency> all = agencyRepository.findAll();
        List<CarRentalAgency> filtered = all.stream()
                .filter(a -> (name == null || a.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(a -> (addr == null || a.getAddress().toLowerCase().contains(addr.toLowerCase())))
                .filter(a -> (gov == null || a.getGovernorate().getName().toLowerCase().contains(gov.toLowerCase())))
                .toList();

        int start = page * size;
        int end = Math.min(start + size, filtered.size());
        return new PageImpl<>(filtered.subList(start, end), PageRequest.of(page, size), filtered.size());
    }


}