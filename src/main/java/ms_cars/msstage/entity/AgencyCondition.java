package ms_cars.msstage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;
@Entity
@Data
public class AgencyCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer minDriverAge;
    private Integer minLicenseAge;

    private Integer minDays;

    private Integer maxKmPerDay; // if this exist ,maxKmPerDayDaysMax must exist
    private Integer maxKmPerDayDaysMax;

    private Boolean extraDriverAllowed; // if this exist ,extraDriverFree must exist
    private Boolean extraDriverFree;

    private Integer extraDriverMinAge;//// extraDriverAllowed is true
    private Integer extraDriverMinLicenseAge; // extraDriverAllowed is true

    @ManyToOne
    @JoinColumn(name = "pickup_location_id")
    private Location pickupLocation;

    @ManyToOne
    @JoinColumn(name = "return_location_id")
    private Location returnLocation;

    @Column(name = "pickup_time")
    private LocalTime pickupTime;
    @Column(name = "return_time")
    private LocalTime returnTime;

    @OneToOne(mappedBy = "condition")
    private CarRentalAgency agency;
}

