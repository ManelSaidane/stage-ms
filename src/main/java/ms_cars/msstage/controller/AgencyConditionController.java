package ms_cars.msstage.controller;

import lombok.AllArgsConstructor;
import ms_cars.msstage.dto.requests.AgencyConditionRequest;
import ms_cars.msstage.entity.AgencyCondition;
import ms_cars.msstage.service.AgencyConditionService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/agencies/conditions")
@AllArgsConstructor
public class AgencyConditionController {

    private final AgencyConditionService service;


    @PostMapping("/{agencyId}")
    public AgencyCondition create(@PathVariable UUID agencyId,
                                  @RequestBody AgencyConditionRequest r) {
        return service.create(agencyId, r);
    }


    @PutMapping("/{condId}")
    public AgencyCondition update(@PathVariable UUID condId,
                                  @RequestBody AgencyConditionRequest r) {
        return service.update(condId, r);
    }


    @GetMapping("/agency/{agencyId}")
    public AgencyCondition get(@PathVariable UUID agencyId) {
        return service.getByAgency(agencyId);
    }

    
    @DeleteMapping("/{condId}")
    public void delete(@PathVariable UUID condId) {
        service.delete(condId);
    }
}

