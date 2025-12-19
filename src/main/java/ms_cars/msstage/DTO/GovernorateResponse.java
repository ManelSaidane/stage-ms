package ms_cars.msstage.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GovernorateResponse {

    private UUID id;
    private String name;
}
