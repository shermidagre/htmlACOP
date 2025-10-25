package org.example.controller;

// Archivo: src/main/java/com/tudominio/controller/RecomendacionesController.java


import org.example.service.IaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api") // O el prefijo que estés usando (ej: /api)
public class IaController {

    private final IaService iaService;

    @Autowired
    public IaController(IaService iaService) {
        this.iaService = iaService;
    }

    /**
     * Endpoint para las peticiones inteligentes del Chatbot.
     * POST http://localhost:8081/api/chat
     * Body: { "prompt": "Cual es la mejor plataforma para RPGs?" }
     */
    @PostMapping("/chat")
    public Map<String, String> getAiResponse(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");

        if (prompt == null || prompt.trim().isEmpty()) {
            return Collections.singletonMap("error", "Se requiere el parámetro 'prompt'.");
        }

        String aiResponse = iaService.getFormalAiResponse(prompt);

        // Retorna la respuesta en el formato { "text": "..." } esperado por el frontend
        return Collections.singletonMap("text", aiResponse);
    }
}