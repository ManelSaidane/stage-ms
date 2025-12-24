package ms_cars.msstage.repository;

import ms_cars.msstage.entity.AgencyCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgencyConditionRepository extends JpaRepository<AgencyCondition, UUID> {
}
