package org.example.dto.noutilizados;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.List;
import java.util.Map;

// Define el formato de los parámetros de la función
@JsonInclude(Include.NON_NULL)
public record Schema(
        String type, // Debe ser "OBJECT" para un mapa de propiedades
        Map<String, SchemaProperty> properties,
        List<String> required
) {}

