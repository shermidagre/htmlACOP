package org.example.dto.noutilizados;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record SchemaProperty( // <--- DEBE SER PUBLIC
                              String type,
                              String description
) {}