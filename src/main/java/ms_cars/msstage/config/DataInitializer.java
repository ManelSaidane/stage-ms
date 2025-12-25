package ms_cars.msstage.config;

import ms_cars.msstage.service.LocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ms_cars.msstage.service.GovernorateService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(GovernorateService governorateService, LocationService locationService) {
        return args -> {
            governorateService.init();

            locationService.init();
        };
    }
}
