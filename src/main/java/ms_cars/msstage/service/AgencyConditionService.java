package ms_cars.msstage.service;

import lombok.AllArgsConstructor;
import ms_cars.msstage.dto.requests.AgencyConditionRequest;
import ms_cars.msstage.entity.AgencyCondition;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.repository.AgencyConditionRepository;
import ms_cars.msstage.repository.CarRentalAgencyRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AgencyConditionService {

    private final AgencyConditionRepository repo;
    private final CarRentalAgencyRepository agencyRepo;

    public AgencyCondition create(UUID agencyId, AgencyConditionRequest r) {

        CarRentalAgency agency = agencyRepo.findById(agencyId)
                .orElseThrow(() -> new RuntimeException("Agency not found"));

        if (agency.getCondition() != null)
            throw new RuntimeException("Condition already exists for this agency");

        AgencyCondition c = new AgencyCondition();

        //  BASIC FIELDS
        c.setMinDriverAge(r.getMinDriverAge());
        c.setMinLicenseAge(r.getMinLicenseAge());
        c.setMinDays(r.getMinDays());
        c.setMaxKmPerDay(r.getMaxKmPerDay());
        c.setMaxKmPerDayDaysMax(r.getMaxKmPerDayDaysMax());

        //EXTRA DRIVER
        c.setExtraDriverAllowed(r.getExtraDriverAllowed());

        if (Boolean.TRUE.equals(r.getExtraDriverAllowed())) {
            c.setExtraDriverFree(r.getExtraDriverFree());
            c.setExtraDriverMinAge(r.getExtraDriverMinAge());
            c.setExtraDriverMinLicenseAge(r.getExtraDriverMinLicenseAge());
        } else {
            c.setExtraDriverFree(null);
            c.setExtraDriverMinAge(null);
            c.setExtraDriverMinLicenseAge(null);
        }


        c.setPickupLocation(r.getPickupLocation());
        c.setReturnLocation(r.getReturnLocation());
        c.setPickupTime(r.getPickupTime());
        c.setReturnTime(r.getReturnTime());

        c = repo.save(c);

        agency.setCondition(c);
        agencyRepo.save(agency);

        return c;
    }

    public AgencyCondition update(UUID condId, AgencyConditionRequest r) {

        AgencyCondition c = repo.findById(condId)
                .orElseThrow(() -> new RuntimeException("Condition not found"));

        //BASIC
        c.setMinDriverAge(r.getMinDriverAge());
        c.setMinLicenseAge(r.getMinLicenseAge());
        c.setMinDays(r.getMinDays());
        c.setMaxKmPerDay(r.getMaxKmPerDay());
        c.setMaxKmPerDayDaysMax(r.getMaxKmPerDayDaysMax());

        //EXTRA
        c.setExtraDriverAllowed(r.getExtraDriverAllowed());

        if (Boolean.TRUE.equals(r.getExtraDriverAllowed())) {
            c.setExtraDriverFree(r.getExtraDriverFree());
            c.setExtraDriverMinAge(r.getExtraDriverMinAge());
            c.setExtraDriverMinLicenseAge(r.getExtraDriverMinLicenseAge());
        } else {
            c.setExtraDriverFree(null);
            c.setExtraDriverMinAge(null);
            c.setExtraDriverMinLicenseAge(null);
        }


        c.setPickupLocation(r.getPickupLocation());
        c.setReturnLocation(r.getReturnLocation());
        c.setPickupTime(r.getPickupTime());
        c.setReturnTime(r.getReturnTime());

        return repo.save(c);
    }

    public AgencyCondition getByAgency(UUID agencyId) {

        return agencyRepo.findById(agencyId)
                .orElseThrow(() -> new RuntimeException("Agency not found"))
                .getCondition();
    }

    public void delete(UUID condId) {

        AgencyCondition c = repo.findById(condId)
                .orElseThrow(() -> new RuntimeException("Condition not found"));

        repo.delete(c);
    }
}
