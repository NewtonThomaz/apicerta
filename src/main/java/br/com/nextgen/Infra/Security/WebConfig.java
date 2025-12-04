package br.com.nextgen.Infra.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // CORREÇÃO CRÍTICA PARA WINDOWS:
        // O .toUri().toString() converte o caminho "C:\Users\..." para "file:///C:/Users/..."
        // Sem isso, o Spring Boot no Windows muitas vezes não consegue ler a pasta e retorna 403 ou 404.
        String uploadPath = Paths.get("uploads").toUri().toString();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}