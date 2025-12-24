package ms_cars.msstage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class AgencyCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


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


    @OneToOne(mappedBy = "condition")
    private CarRentalAgency agency;
}

