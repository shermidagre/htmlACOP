package org.example.service;

import org.example.dto.*; // Importamos los DTOs que creamos
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatbotService {

    private final WebClient webClient;
    private final String apiKey;
    private static final String MODEL = "gemini-2.5-flash";

    // 1. Inyectamos la clave y configuramos el WebClient.
    public ChatbotService(@Value("${gemini.api.key}") String apiKey) {
        this.apiKey = apiKey;
        String baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/";

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String obtenerRespuesta(String mensajeUsuario) {

        // Configuración: Eres un profesor de Java útil.
        String systemInstruction = "Tu rol es de ser un gamer friki de los juegos, el cual le gusta ayudar a los demas a elegir juegos segun sus gustos o categorias, le ayudaras a poder elegir posibles juegos que le puedan gustar, eres flexible y tambien contestas amablemente a las preguntas, si notas que la conversacion se va del tema, mandaras un mensaje diciendo : Volvamos a el tema de los videojuegos, es lo que me apasiona";
        String promptCompleto = systemInstruction + " " + mensajeUsuario;

        // 2. Crear la estructura de la solicitud JSON usando los DTOs.
        Part userPart = new Part(promptCompleto);
        Content userContent = new Content(List.of(userPart));
        GeminiRequest geminiRequest = new GeminiRequest(List.of(userContent));

        // 3. Ejecutar la llamada REST (Asíncrona, bloqueamos para hacerla síncrona).
        Mono<GeminiResponse> responseMono = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(MODEL + ":generateContent")
                        .queryParam("key", apiKey) // Se añade la API Key al query
                        .build())
                .bodyValue(geminiRequest)
                .retrieve()
                .bodyToMono(GeminiResponse.class);

        GeminiResponse response = responseMono.block();

        // 4. Extraer el texto de la respuesta.
        if (response != null && !response.candidates().isEmpty()) {
            // Navegamos a través de la estructura anidada: candidates -> content -> parts -> text
            return response.candidates().get(0).content().parts().stream()
                    .map(Part::text)
                    .collect(Collectors.joining());
        }

        return "Lo siento, hubo un error al obtener la respuesta de la IA.";
    }
}