package ms_cars.msstage.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateCarRentalAgencyRequest {

    @NotBlank(message = "Agency name is required")
    private String name;

    @NotBlank(message = "Agency address is required")
    private String address;

    @NotEmpty(message = "At least one governorate is required")
    private List<UUID> governorateIds;
}
