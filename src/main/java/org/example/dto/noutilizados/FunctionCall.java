package org.example.dto.noutilizados;

import java.util.Map;

public record FunctionCall(
        String name,
        Map<String, String> args // <-- DEBE SER STRING O OBJECT
) {}