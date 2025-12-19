package ms_cars.msstage.service;

import lombok.RequiredArgsConstructor;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.repository.CarRentalAgencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentalAgencyService {

    private final CarRentalAgencyRepository agencyRepository;

    public List<CarRentalAgency> findAll() {
        return agencyRepository.findAll();
    }

    public CarRentalAgency findById(Long id) {
        return agencyRepository.findById(id).orElse(null);
    }

    public CarRentalAgency save(CarRentalAgency agency) {
        return agencyRepository.save(agency);
    }

    public void deleteById(Long id) {
        agencyRepository.deleteById(id);
    }
}