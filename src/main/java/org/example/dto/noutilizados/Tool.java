package org.example.dto.noutilizados;

import java.util.List;

// Lista de herramientas/funciones disponibles para el modelo
public record Tool(
        List<FunctionDeclaration> functionDeclarations
) {}