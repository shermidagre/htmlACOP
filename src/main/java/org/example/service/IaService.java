package org.example.service;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class IaService {

    @Value("${ai.gemini.api-key}")
    private String geminiApiKey;

    // URL del endpoint de generación de texto de Gemini (para gemini-2.5-flash)
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    // Instrucción de sistema para la personalidad formal e inteligente
    private static final String SYSTEM_INSTRUCTION =
            "Eres un asistente de recomendación de juegos experto. Responde a todas las preguntas de manera extremadamente formal, concisa y educada. Tu objetivo es informar y guiar al usuario. Si te preguntan por recomendaciones, ofrece dos o tres opciones y sus plataformas. Mantente profesional en todo momento.";

    private final WebClient webClient;

    public IaService(WebClient.Builder webClientBuilder) {
        // Inicializa WebClient para llamadas HTTP asíncronas
        this.webClient = webClientBuilder.baseUrl("").build();
    }

    /**
     * Llama a la API de Gemini con una personalidad formal.
     * @param prompt La pregunta del usuario.
     * @return La respuesta formal generada por la IA.
     */
    public String getFormalAiResponse(String prompt) {

        // Estructura del Body que espera la API de Gemini
        Map<String, Object> config = Map.of("systemInstruction", SYSTEM_INSTRUCTION);
        Map<String, Object> userContent = Map.of(
                "role", "user",
                "parts", new Object[]{Map.of("text", prompt)}
        );
        Map<String, Object> body = Map.of(
                "contents", new Object[]{userContent},
                "config", config
        );

        // Realiza la llamada RESTful a la API de Gemini
        // Usamos block() para que la llamada sea síncrona en el contexto del Controller
        return webClient.post()
                .uri(GEMINI_API_URL + geminiApiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class) // Esperamos un mapa JSON como respuesta
                .map(response -> {
                    // Navegación compleja para extraer el texto de la respuesta
                    try {
                        Map<String, Object> candidate = (Map<String, Object>) ((java.util.List) response.get("candidates")).get(0);
                        Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                        Map<String, Object> part = (Map<String, Object>) ((java.util.List) content.get("parts")).get(0);
                        return (String) part.get("text");
                    } catch (Exception e) {
                        throw new RuntimeException("Fallo al parsear la respuesta de Gemini: " + e.getMessage());
                    }
                })
                .onErrorResume(e -> {
                    System.err.println("Error en la comunicación con la API de Gemini: " + e.getMessage());
                    return Mono.just("Disculpe. Hemos experimentado un fallo en la conexión con la inteligencia artificial. Se ruega inténtelo de nuevo.");
                })
                .block(); // Bloquea hasta obtener la respuesta (o maneja con Reactor si prefieres asíncrono)
    }
}