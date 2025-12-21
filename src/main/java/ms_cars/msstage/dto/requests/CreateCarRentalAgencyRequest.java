package ms_cars.msstage.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateCarRentalAgencyRequest {

    @NotBlank(message = "Agency name is required")
    private String name;

    @NotBlank(message = "Agency address is required")
    private String address;

    @NotEmpty(message = " governorate is required")
    private UUID governorateId;

    private String fileName;

}
