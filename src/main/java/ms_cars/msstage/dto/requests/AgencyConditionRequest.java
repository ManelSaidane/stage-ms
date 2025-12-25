package ms_cars.msstage.dto.requests;

import lombok.Data;
import ms_cars.msstage.entity.Location;

import java.time.LocalTime;
import java.util.UUID;

@Data

public class AgencyConditionRequest {

    private Integer minDriverAge;
    private Integer minLicenseAge;
    private Integer minDays;

    private Integer maxKmPerDay;
    private Integer maxKmPerDayDaysMax;

    private Boolean extraDriverAllowed;
    private Boolean extraDriverFree;

    private Integer extraDriverMinAge;
    private Integer extraDriverMinLicenseAge;

    private Location pickupLocation;
    private Location returnLocation;



    private LocalTime pickupTime;
    private LocalTime returnTime;
}
