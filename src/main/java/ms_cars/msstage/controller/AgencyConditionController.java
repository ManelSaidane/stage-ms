package ms_cars.msstage.controller;

import lombok.AllArgsConstructor;
import ms_cars.msstage.dto.requests.AgencyConditionRequest;
import ms_cars.msstage.dto.responses.ApiResponse;
import ms_cars.msstage.entity.AgencyCondition;
import ms_cars.msstage.entity.Location;
import ms_cars.msstage.service.AgencyConditionService;
import ms_cars.msstage.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agencies/conditions")
@AllArgsConstructor
public class AgencyConditionController {

    private final AgencyConditionService service;
    private final LocationService locationService;

    // CREATE condition for agency
    @PostMapping("/{agencyId}")
    public ResponseEntity<ApiResponse<AgencyCondition>> create(
            @PathVariable String agencyId,
            @RequestBody AgencyConditionRequest r) {

        UUID uuid;
        try {
            uuid = UUID.fromString(agencyId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Invalid UUID format"));
        }

        AgencyCondition condition = service.create(uuid, r);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(condition, "Condition created successfully"));
    }

    // UPDATE condition
    @PutMapping("/{condId}")
    public ResponseEntity<ApiResponse<AgencyCondition>> update(
            @PathVariable UUID condId,
            @RequestBody AgencyConditionRequest r) {

        AgencyCondition updated = service.update(condId, r);
        return ResponseEntity.ok(
                ApiResponse.ok(updated, "Condition updated successfully")
        );
    }

    // GET condition by agency
    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<ApiResponse<AgencyCondition>> get(
            @PathVariable UUID agencyId) {

        AgencyCondition condition = service.getByAgency(agencyId);
        if (condition == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Condition not found for this agency"));
        }
        return ResponseEntity.ok(
                ApiResponse.ok(condition, "Agency condition retrieved")
        );
    }

    // DELETE condition
    @DeleteMapping("/{condId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID condId) {
        service.delete(condId);
        return ResponseEntity.ok(
                ApiResponse.ok(null, "Condition deleted successfully")
        );
    }

    // GET all locations
    @GetMapping("/locations")
    public ResponseEntity<ApiResponse<List<Location>>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(
                ApiResponse.ok(locations, "Locations retrieved successfully")
        );
    }
}
