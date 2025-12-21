package ms_cars.msstage.dto.requests;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCarRentalRequest {

    @NotBlank(message = "id is required")
    private UUID id;

    private String name;

    private String address;

    private UUID governorateId;

    private String fileName;
}
