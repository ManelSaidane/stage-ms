package ms_cars.msstage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize(); //"Paths.get():"Converts the string path into a path object
        // Create folder if it doesn't exist
        Files.createDirectories(this.uploadDir);
    }

    /**
     * Store the file and return its filename
     */
    public String storeFile(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";

        int index = originalFileName.lastIndexOf(".");
        if (index > 0) {
            extension = originalFileName.substring(index);
        }

        // Generate unique filename
        String fileName = UUID.randomUUID() + extension;
        Path targetLocation = this.uploadDir.resolve(fileName);

        // Copy file to the target location
        Files.copy(file.getInputStream(), targetLocation);

        return fileName;
    }

    /**
     * Get the full path to a stored file
     */
    public Path getFilePath(String fileName) {
        return this.uploadDir.resolve(fileName).normalize();
    }

    public boolean fileExists(String fileName) {
        Path filePath = getFilePath(fileName);
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }

    public void deleteFile(String fileName) throws IOException {
        if (fileName == null || fileName.trim().isEmpty()) {
            return; // Rien à faire si pas de nom fourni
        }


        Path filePath = uploadDir.resolve(fileName.trim()).normalize();

        try {

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Fichier supprimé avec succès : " + filePath);
            } else {
                System.out.println("Le fichier n'existe pas, suppression ignorée : " + filePath);
            }
        } catch (DirectoryNotEmptyException e) {
            throw new IOException("Impossible de supprimer : le chemin est un dossier non vide - " + filePath, e);
        } catch (IOException e) {
            throw new IOException("Erreur lors de la suppression du fichier : " + filePath, e);
        }
    }
}
