package br.com.clientejacrm.service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentRequest;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.GenerationConfig;
import com.google.genai.types.Part;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GeminiService {

    @ConfigProperty(name = "google.api.key")
    String apiKey;

    private Client client;
    private static final String MODEL = "gemini-1.5-pro-002";

    @PostConstruct
    void init() {
        client = new Client.Builder()
                .apiKey(apiKey)
                .build();
    }

    public String generateText(String pergunta) {
        String systemPtBr = """
                Você é um assistente técnico. Responda SEMPRE em português do Brasil,
                em tom formal e profissional, valorizando a clareza e a precisão.
                """;

        GenerationConfig genCfg = new GenerationConfig.Builder()
                .temperature(0.2)
                .topP(0.9)
                .maxOutputTokens(1024)
                .responseMimeType("text/plain")
                .build();

        Content system = Content.newBuilder()
                .addParts(Part.newBuilder().setText(systemPtBr))
                .build();

        Content user = Content.newBuilder()
                .addParts(Part.newBuilder().setText("Responda em português do Brasil. Pergunta: " + pergunta))
                .build();

        GenerateContentRequest req = GenerateContentRequest.newBuilder()
                .model(MODEL)
                .systemInstruction(system)
                .addContents(user)
                .generationConfig(genCfg)
                .build();

        GenerateContentResponse resp = client.models().generateContent(req);
        return resp.getText();
    }
}
