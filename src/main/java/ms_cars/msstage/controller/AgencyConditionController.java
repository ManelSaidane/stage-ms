package ms_cars.msstage.controller;

import lombok.AllArgsConstructor;
import ms_cars.msstage.dto.requests.AgencyConditionRequest;
import ms_cars.msstage.entity.AgencyCondition;
import ms_cars.msstage.entity.Location;
import ms_cars.msstage.service.AgencyConditionService;
import ms_cars.msstage.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AgencyCondition> create(
            @PathVariable UUID agencyId,
            @RequestBody AgencyConditionRequest r) {

        return new ResponseEntity<>(
                service.create(agencyId, r),
                HttpStatus.CREATED
        );
    }

    // UPDATE condition
    @PutMapping("/{condId}")
    public ResponseEntity<AgencyCondition> update(
            @PathVariable UUID condId,
            @RequestBody AgencyConditionRequest r) {

        return ResponseEntity.ok(
                service.update(condId, r)
        );
    }

    // GET condition by agency
    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<AgencyCondition> get(
            @PathVariable UUID agencyId) {

        return ResponseEntity.ok(
                service.getByAgency(agencyId)
        );
    }

    // DELETE condition
    @DeleteMapping("/{condId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID condId) {
        service.delete(condId);
    }


    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }
}
