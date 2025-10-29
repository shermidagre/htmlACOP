package org.example.dto.noutilizados;

// Describe la función que el modelo puede llamar
public record FunctionDeclaration(
        String name,
        String description,
        Schema parameters
) {}