package com.project.board_back.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiServiceImpl {

    private final WebClient client;

    public GeminiService(@Value("${gemini.api.url}") String url,
                         @Value("${gemini.api.key}") String apiKey,
                         @Value("${gemini.api.secret}") String apiSecret) {
        this.client = WebClient.builder()
                .baseUrl(url)
                .defaultHeaders(h -> {
                    h.setBearerAuth(apiKey); // 필요한 auth 방식에 맞춰 수정
                    h.add("API-SECRET", apiSecret);
                })
                .build();
    }

    public String askGemini(String input) {
        var resp = client.post()
                .uri("/query")
                .bodyValue(Map.of("input", input))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return resp != null ? (String) resp.get("output") : "error";
    }
}
