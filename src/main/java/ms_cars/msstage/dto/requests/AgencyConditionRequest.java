package ms_cars.msstage.dto.requests;


import lombok.Data;

@Data
public class AgencyConditionRequest {


    private int minDriverAge;
    private int minLicenseAge;


    private int minDays;
    private int maxKmPerDay;


    private boolean extraDriverAllowed;
    private boolean extraDriverFree;
    private int extraDriverMinAge;
    private int extraDriverMinLicenseAge;


    private String pickupLocation;
    private String returnLocation;
    private String pickupTime;
    private String returnTime;
}

