package org.example.dto.noutilizados;

import java.util.Map;

// El resultado de la función ejecutada localmente que se envía a Gemini
public record FunctionResponse(
        String name,
        Map<String, Object> response
) {}