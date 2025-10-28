package org.example.dto;

// La API de Gemini puede devolver múltiples "candidatos" de respuesta.
// Solo necesitamos el primer 'content'.
public record Candidate(Content content) {}