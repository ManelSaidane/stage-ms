package ms_cars.msstage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "car_rental_agencies")
public class CarRentalAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String address;
    @ManyToOne
    @JoinColumn(name = "governorate_id", nullable = false)
    private Governorate governorate;

    @Column(name = "logo_image")
    private String logoImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condition_id")
    private AgencyCondition condition;


    public CarRentalAgency() {
    }

}