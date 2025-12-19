package ms_cars.msstage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

@Entity
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

    @ManyToMany
    @JoinTable(
            name = "agency_governorates",
            joinColumns = @JoinColumn(name = "agency_id"),
            inverseJoinColumns = @JoinColumn(name = "governorate_id")
    )
    private Set<Governorate> governorates;

    @Column(name = "logo_image")
    private String logoImage;


    public CarRentalAgency() {}

    public CarRentalAgency(UUID id, String name, String address,
                           Set<Governorate> governorates, String logoImage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.governorates = governorates;
        this.logoImage = logoImage;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Governorate> getGovernorates() {
        return governorates;
    }

    public void setGovernorates(Set<Governorate> governorates) {
        this.governorates = governorates;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }
}