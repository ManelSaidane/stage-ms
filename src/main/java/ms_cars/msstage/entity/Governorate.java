package ms_cars.msstage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(name = "governorates",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Governorate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;


    public Governorate() {}

    public Governorate(UUID id, String name) {
        this.id = id;
        this.name = name;
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
}