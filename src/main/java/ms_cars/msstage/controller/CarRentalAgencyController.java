package ms_cars.msstage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ms_cars.msstage.entity.CarRentalAgency;
import ms_cars.msstage.service.CarRentalAgencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agencies")
@RequiredArgsConstructor
public class CarRentalAgencyController {

    private final CarRentalAgencyService agencyService;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public List<CarRentalAgency> getAllAgencies() {
        return agencyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarRentalAgency> getAgencyById(@PathVariable Long id) {
        CarRentalAgency agency = agencyService.findById(id);
        return agency == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(agency);
    }


    @PostMapping
    public ResponseEntity<CarRentalAgency> createAgency(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("governorate") String governorate,
            @RequestParam("logoFile") MultipartFile logoFile) throws IOException {

        CarRentalAgency agency = CarRentalAgency.builder()
                .name(name)
                .address(address)
                .governorate(governorate)
                .build();

        if (!logoFile.isEmpty()) {
            String logoFileName = saveUploadedFile(logoFile);
            agency.setLogoImage(logoFileName);
        }

        CarRentalAgency saved = agencyService.save(agency);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CarRentalAgency> updateAgency(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("governorate") String governorate,
            @RequestParam(value = "logoFile", required = false) MultipartFile logoFile) throws IOException {

        CarRentalAgency existing = agencyService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setName(name);
        existing.setAddress(address);
        existing.setGovernorate(governorate);

        if (logoFile != null && !logoFile.isEmpty()) {
            String logoFileName = saveUploadedFile(logoFile);
            existing.setLogoImage(logoFileName);
        }

        CarRentalAgency updated = agencyService.save(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        CarRentalAgency existing = agencyService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        agencyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";

        String fileName = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return "/uploads/" + fileName;
    }
}