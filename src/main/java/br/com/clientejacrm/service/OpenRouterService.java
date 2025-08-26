package br.com.clientejacrm.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class OpenRouterService {

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    @PostConstruct
    void init() {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        objectMapper = new ObjectMapper();
    }

    @ConfigProperty(name = "openrouter.api.key")
    String apiKey;

    // Permite trocar modelos via configuração sem recompilar
    @ConfigProperty(name = "openrouter.model.primary", defaultValue = "google/gemma-2-9b-it:free")
    String modelPrimary;

    @ConfigProperty(name = "openrouter.model.fallback", defaultValue = "meta-llama/llama-3.2-3b-instruct:free")
    String modelFallback;


    @ConfigProperty(name = "openrouter.referer", defaultValue = "http://localhost:8080/")
    String httpReferer;

    @ConfigProperty(name = "openrouter.title", defaultValue = "ClienteJACRM")
    String xTitle;

    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";

    private static final String SYSTEM_PTBR = """
        Você é um assistente técnico.
        Responda EXCLUSIVAMENTE em português do Brasil (pt-BR), em tom formal e profissional.
        Se a resposta não estiver em pt-BR, TRADUZA-A e devolva apenas a versão em pt-BR.
        Evite gírias, mantenha objetividade e clareza.
        """;

    public String generateText(String prompt) {
        ensureApiKey();

        // 1) Tenta com o modelo primário
        String result = callModel(modelPrimary, prompt);
        if (result != null && !result.isBlank()) {
            return result;
        }

        // 2) Fallback automático (útil para limite/carga)
        if (modelFallback != null && !modelFallback.isBlank()
                && !modelFallback.equalsIgnoreCase(modelPrimary)) {
            String fallback = callModel(modelFallback, prompt);
            if (fallback != null && !fallback.isBlank()) {
                return fallback;
            }
        }

        throw new RuntimeException("Falha ao obter resposta da OpenRouter API com modelos configurados.");
    }

    private String callModel(String model, String prompt) {
        try {
            // messages: system + user
            List<Map<String, Object>> messages = List.of(
                    Map.of("role", "system", "content", SYSTEM_PTBR),
                    Map.of("role", "user", "content", prompt)
            );

            Map<String, Object> payload = new HashMap<>();
            payload.put("model", model);
            payload.put("messages", messages);
            payload.put("temperature", 0.2);
            payload.put("top_p", 0.9);
            payload.put("max_tokens", 1024);

            String body = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENROUTER_URL))
                    .timeout(Duration.ofSeconds(30))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .header("HTTP-Referer", httpReferer) // usar URL válida do seu app
                    .header("X-Title", xTitle)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            // Trata HTTP != 200 imediatamente
            if (response.statusCode() != 200) {
                // Tenta expor o erro retornado pela API
                String apiErr = tryExtractApiError(response.body());
                throw new RuntimeException("HTTP " + response.statusCode() + " ao chamar OpenRouter (" + model + "): " + apiErr);
            }

            JsonNode json = objectMapper.readTree(response.body());
            // Estrutura padrão: { choices: [ { message: { role, content } } ] }
            JsonNode choices = json.path("choices");
            if (!choices.isArray() || choices.size() == 0) {
                throw new RuntimeException("Resposta sem 'choices' válidos (" + model + "): " + response.body());
            }

            JsonNode first = choices.get(0);
            JsonNode message = first.path("message");
            String content = message.path("content").asText("");

            if (content.isBlank()) {
                // Algumas implementações retornam em outro campo; cheque 'delta' se streaming for usado (aqui não é)
                throw new RuntimeException("Conteúdo vazio na resposta (" + model + "): " + response.body());
            }

            return content.trim();

        } catch (Exception e) {
            // Logue internamente se desejar; aqui retornamos null para permitir o fallback
            return null;
        }
    }

    private String tryExtractApiError(String body) {
        try {
            JsonNode err = objectMapper.readTree(body).path("error");
            if (!err.isMissingNode()) {
                // openrouter costuma trazer { "error": { "message": "...", ... } }
                String msg = err.path("message").asText();
                if (msg != null && !msg.isBlank()) return msg;
            }
        } catch (Exception ignored) {
        }
        return body; // devolve o corpo bruto se não conseguir parsear
    }

    private void ensureApiKey() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Propriedade 'openrouter.api.key' não configurada.");
        }
    }
}
