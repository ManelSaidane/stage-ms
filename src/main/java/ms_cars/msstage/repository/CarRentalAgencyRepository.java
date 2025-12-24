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
            "WHERE (:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:addr IS NULL OR LOWER(a.address) LIKE LOWER(CONCAT('%', :addr, '%'))) " +
            "AND (:gov IS NULL OR LOWER(a.governorate.name) LIKE LOWER(CONCAT('%', :gov, '%'))) ")
    Page<CarRentalAgency> search(@Param("name") String name,
                                 @Param("addr") String addr,
                                 @Param("gov") String gov,
                                 Pageable pageable);
}
