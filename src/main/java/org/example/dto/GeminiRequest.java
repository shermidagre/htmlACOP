package org.example.dto;

import java.util.List;

// El cuerpo completo de la solicitud que envías al endpoint generateContent
public record GeminiRequest(List<Content> contents) {}