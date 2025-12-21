package ms_cars.msstage.repository;

import ms_cars.msstage.entity.CarRentalAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRentalAgencyRepository extends JpaRepository<CarRentalAgency, UUID> {

}