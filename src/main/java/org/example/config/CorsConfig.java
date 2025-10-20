package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global para habilitar CORS (Cross-Origin Resource Sharing).
 *
 * Esto permite que el frontend (tu archivo JS) acceda al API de Render,
 * ya que se encuentran en diferentes dominios (orígenes).
 *
 * IMPORTANTE: Para producción, se recomienda cambiar 'http://*' por
 * el dominio específico de tu frontend (ej: 'https://tuservidor-frontend.com').
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Habilita CORS para todos los endpoints (/**) en tu API.
        registry.addMapping("/**")
                // Permite solicitudes desde cualquier origen (*)
                // Esto es adecuado para pruebas/desarrollo, pero se debe limitar en producción.
                .allowedOrigins("*")
                // Permite todos los métodos HTTP (GET, POST, PUT, DELETE, etc.)
                .allowedMethods("*")
                // Permite todos los encabezados
                .allowedHeaders("*");
    }
}