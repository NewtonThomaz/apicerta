package br.com.nextgen.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    // Define o caminho da pasta onde as imagens serão salvas
    // "uploads" será criado na raiz do projeto
    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório de upload.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normaliza o nome do arquivo
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Gera um nome único para evitar sobrescrever arquivos com mesmo nome
        // Ex: avatar.png vira -> uuid-aleatorio_avatar.png
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try {
            // Verifica se o nome do arquivo contém caracteres inválidos
            if(fileName.contains("..")) {
                throw new RuntimeException("Nome de arquivo inválido " + fileName);
            }

            // Copia o arquivo para o local de destino (substituindo se existir)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível armazenar o arquivo " + fileName, ex);
        }
    }
}