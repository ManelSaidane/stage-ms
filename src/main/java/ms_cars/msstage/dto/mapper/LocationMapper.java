package ms_cars.msstage.dto.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Context;

import ms_cars.msstage.dto.requests.LocationRequest;
import ms_cars.msstage.entity.Governorate;
import ms_cars.msstage.entity.Location;
import ms_cars.msstage.repository.GovernorateRepository;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "governorate", ignore = true)
    Location toEntity(LocationRequest request, @Context GovernorateRepository governorateRepository);

    List<Location> toEntityList(
            List<LocationRequest> list,
            @Context GovernorateRepository governorateRepository);

    @AfterMapping
    default void mapGovernorate(
            LocationRequest request,
            @MappingTarget Location location,
            @Context GovernorateRepository governorateRepository) {

        if (request.isFullCountry()) {
            location.setFullCountry(true);
            location.setGovernorate(null);
            location.setAirport(null);
            return;
        }

        if (request.getGovernorateId() != null) {
            Governorate governorate = governorateRepository.findById(request.getGovernorateId())
                    .orElseThrow(() -> new IllegalArgumentException("Governorate not found"));

            location.setGovernorate(governorate);
            location.setAirport(null);
            location.setFullCountry(false);
        }

        if (request.getAirport() != null) {
            location.setAirport(request.getAirport());
            location.setGovernorate(null);
            location.setFullCountry(false);
        }
    }
}
