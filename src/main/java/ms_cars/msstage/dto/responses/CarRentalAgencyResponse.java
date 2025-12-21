package ms_cars.msstage.dto.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CarRentalAgencyResponse {

    private UUID id;
    private String name;
    private String address;
    private List<GovernorateResponse> governorates;
}
