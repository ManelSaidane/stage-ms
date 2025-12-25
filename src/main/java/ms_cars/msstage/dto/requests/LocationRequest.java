package ms_cars.msstage.dto.requests;

import java.util.UUID;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import ms_cars.msstage.entity.Airport;

@Data
public class LocationRequest {

    private boolean fullCountry;

    private UUID governorateId;

    @Enumerated(EnumType.STRING)
    private Airport airport;
}
