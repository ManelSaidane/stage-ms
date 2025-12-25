package ms_cars.msstage.repository;

import ms_cars.msstage.entity.CarRentalAgency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRentalAgencyRepository extends JpaRepository<CarRentalAgency, UUID> {

    @Query("SELECT a FROM CarRentalAgency a " +
            "WHERE (:name IS NULL OR UPPER(a.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
            "AND (:address IS NULL OR UPPER(a.address) LIKE UPPER(CONCAT('%', :address, '%'))) " +
            "AND (:governorate IS NULL OR UPPER(a.governorate.name) LIKE UPPER(CONCAT('%', :governorate, '%')))")
    Page<CarRentalAgency> search(@Param("name") String name,
                                 @Param("address") String address,
                                 @Param("governorate") String governorate,
                                 Pageable pageable);
}
