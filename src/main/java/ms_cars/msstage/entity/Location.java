package ms_cars.msstage.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "locations")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_country", nullable = false)
    private boolean fullCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "governorate_id")
    private Governorate governorate;

    @Column(name = "airport", nullable = true)
    @Enumerated(EnumType.STRING)
    private Airport airport;

    public Location(Governorate governorate) {
        this.governorate = governorate;
        this.fullCountry = false;
        this.airport = null;
    }

    public Location(Airport airport) {
        this.airport = airport;
        this.governorate = null;
        this.fullCountry = false;
    }

    public Location() {
        this.fullCountry = true;
        this.governorate = null;
        this.airport = null;
    }

}