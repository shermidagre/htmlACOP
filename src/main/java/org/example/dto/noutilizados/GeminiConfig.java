package org.example.dto.noutilizados;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;

// CRÍTICO: Asegura que si la lista de herramientas es null, el campo 'tools' se omita.
@JsonInclude(Include.NON_NULL)
public record GeminiConfig(
        List<Tool> tools
        // No debe haber otros campos, o si los hay, deben tener la anotación de omisión de nulos.
) {}