package org.example.dto.noutilizados;

import org.example.dto.ChatBot.Content;

import java.util.List;

public record GeminiResponseRequest(
        List<Content> contents
) {}