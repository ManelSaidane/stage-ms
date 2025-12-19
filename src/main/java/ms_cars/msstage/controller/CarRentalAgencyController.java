package ms_cars.msstage.controller;

import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.service.CarRentalAgencyService;
import ms_cars.msstage.shared.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agencies")
public class CarRentalAgencyController {

    private final CarRentalAgencyService agencyService;

    public CarRentalAgencyController(CarRentalAgencyService agencyService) {
        this.agencyService = agencyService;
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<CarRentalAgency>> createAgency(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam(value = "governorateIds", required = false) List<UUID> governorateIds,
            @RequestPart(value = "logo", required = false) MultipartFile logoFile) {

        ApiResponse<CarRentalAgency> response = agencyService.createAgency(name, address, governorateIds, logoFile);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<CarRentalAgency>>> getAllAgencies() {
        return ResponseEntity.ok(agencyService.getAllAgencies());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarRentalAgency>> getAgencyById(@PathVariable UUID id) {
        ApiResponse<CarRentalAgency> response = agencyService.getAgencyById(id);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }


    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<CarRentalAgency>> updateAgency(
            @PathVariable UUID id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "governorateIds", required = false) List<UUID> governorateIds,
            @RequestPart(value = "logo", required = false) MultipartFile logoFile) {

        ApiResponse<CarRentalAgency> response = agencyService.updateAgency(id, name, address, governorateIds, logoFile);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAgency(@PathVariable UUID id) {
        ApiResponse<Void> response = agencyService.deleteAgency(id);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}