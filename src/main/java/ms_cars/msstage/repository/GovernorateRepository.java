package ms_cars.msstage.repository;

import ms_cars.msstage.entity.Governorate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GovernorateRepository extends JpaRepository<Governorate, UUID> {
}