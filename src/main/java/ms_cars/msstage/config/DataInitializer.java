package ms_cars.msstage.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ms_cars.msstage.service.GovernorateService;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(GovernorateService governorateService) {
        return args -> {
            governorateService.init();
        };
    }
}
