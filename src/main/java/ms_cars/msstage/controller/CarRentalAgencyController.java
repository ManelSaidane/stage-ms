package ms_cars.msstage.controller;

import ms_cars.msstage.dto.requests.CreateCarRentalAgencyRequest;
import ms_cars.msstage.dto.requests.UpdateCarRentalRequest;
import ms_cars.msstage.dto.responses.ApiResponse;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.service.CarRentalAgencyService;

import ms_cars.msstage.service.GovernorateService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agencies-services/agencies")
@AllArgsConstructor
public class CarRentalAgencyController {

    private final CarRentalAgencyService agencyService;
    private final GovernorateService governorateService;
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

    @GetMapping("/gouv")
    public List<Governorate> getAll() {
        return governorateService.getAllGovernorates();
    }


    @GetMapping("/pagination")
    public ApiResponse<Page<CarRentalAgency>> getAllAgencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            return agencyService.getAllAgencies(page, size);
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage());
        }
    }



    // 🔍 filtrer par nom
    @GetMapping("/filter/name")
    public ApiResponse<Page<CarRentalAgency>> byName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                agencyService.byName(name, page, size)
        );
    }


    // 🔍 filtrer par adresse
    @GetMapping("/filter/address")
    public ApiResponse<Page<CarRentalAgency>> byAddress(
            @RequestParam String addr,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                agencyService.byAddr(addr, page, size)
        );
    }


    // 🔍 filtrer par gouvernorat
    @GetMapping("/filter/gov")
    public ApiResponse<Page<CarRentalAgency>> byGov(
            @RequestParam String gov,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                agencyService.byGov(gov, page, size)
        );
    }



    // 🔍 filtrer par tous (name + addr + gov)
    @GetMapping("/filter")
    public ApiResponse<Page<CarRentalAgency>> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String addr,
            @RequestParam(required = false) String gov,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                agencyService.filter(name, addr, gov, page, size)
        );
    }


}