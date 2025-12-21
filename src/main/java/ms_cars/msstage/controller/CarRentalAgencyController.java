package ms_cars.msstage.controller;

import ms_cars.msstage.dto.requests.CreateCarRentalAgencyRequest;
import ms_cars.msstage.dto.requests.UpdateCarRentalRequest;
import ms_cars.msstage.dto.responses.ApiResponse;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.service.CarRentalAgencyService;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agencies-services/agencies")
@AllArgsConstructor
public class CarRentalAgencyController {

    private final CarRentalAgencyService agencyService;

    /**
     * Create a new agency
     */
    @PostMapping("/")
    public ApiResponse<CarRentalAgency> createAgency(@RequestBody CreateCarRentalAgencyRequest request) {
        try {
            CarRentalAgency agency = agencyService.createAgency(request);
            return ApiResponse.ok(agency, "Agency created successfully");
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * Get all agencies
     */
    @GetMapping("/")
    public ApiResponse<List<CarRentalAgency>> getAllAgencies() {
        try {
            return agencyService.getAllAgencies();
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * Get an agency by ID
     */
    @GetMapping("/{id}")
    public ApiResponse<CarRentalAgency> getAgencyById(@PathVariable UUID id) {
        try {
            return agencyService.getAgencyById(id);
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * Update an existing agency
     */
    @PutMapping("/")
    public ApiResponse<CarRentalAgency> updateAgency(@RequestBody UpdateCarRentalRequest request) {
        try {
            CarRentalAgency agency = agencyService.updateAgency(request);
            return ApiResponse.ok(agency, "Agency updated successfully");
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    /**
     * Delete an agency by ID
     */
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAgency(@PathVariable UUID id) {
        try {
            agencyService.deleteAgency(id);
            return ApiResponse.ok("Agency deleted successfully");
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}