package org.example.service;

import org.example.dto.ChatBot.Content;
import org.example.dto.ChatBot.GeminiRequest;
import org.example.dto.ChatBot.GeminiResponse;
import org.example.dto.ChatBot.Part;
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
    
    //Aun no funciona , comprobar el prompt completo
    private boolean PreguntaPaginaWeb(String mensaje) {
        if (mensaje == null || mensaje.isBlank()) return false;

        String texto = mensaje.toLowerCase();



        // Frases comunes
        List<String> frases = List.of(
                "Teneis paginas propias de juegos?","Pagina web","Pagina web de Oliver","agina web","pagina web de oliver","pagina oliver","Pagina oliver"
        );

        boolean tieneFrase = frases.stream().anyMatch(texto::contains);

        // Solo activamos si hay una intención clara (ej: "web" + verbo o pregunta)
        return (tieneFrase) &&
                (texto.contains("tu") || texto.contains("tú") ||
                        texto.contains("dónde") || texto.contains("donde") ||
                        texto.contains("me das") || texto.contains("página"));
    }

    public String obtenerRespuesta(String mensajeUsuario) {

        if (PreguntaPaginaWeb(mensajeUsuario)) {
            return "Esta es la página webde Oliver: https://xogosaqui.itch.io/";
        }

        // Configuración: Eres un profesor de Java útil.
        String systemInstruction = "Tu rol es ser un experto, apasionado y 'friki' de los videojuegos. " +
                "Tu objetivo es ayudar al usuario a elegir juegos, categorías y resolver dudas del sector. " +
                "**Directriz de Tono y Formato:** Responde de forma **concisa, clara y directa** como un experto, limitando la extensión de tus respuestas a **3-4 frases como máximo**." +
                "**Regla de Desviación:** Si la conversación se desvía del tema (videojuegos, consolas, o el sector), responde inmediatamente con este mensaje único: **'Volvamos al tema de los videojuegos, ¡es lo que me apasiona!'**";


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