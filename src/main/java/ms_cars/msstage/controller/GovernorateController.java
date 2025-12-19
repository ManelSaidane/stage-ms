package ms_cars.msstage.controller;

import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.service.GovernorateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/governorates")
public class GovernorateController {

    private final GovernorateService governorateService;

    public GovernorateController(GovernorateService governorateService) {
        this.governorateService = governorateService;
    }

    @GetMapping
    public ResponseEntity<List<Governorate>> getAll() {
        return ResponseEntity.ok(governorateService.getAllGovernorates());
    }
}