package org.example.dto;

import java.util.List;

// Representa el bloque de contenido, que contiene una lista de partes (la "Part" de texto)
public record Content(List<Part> parts) {}