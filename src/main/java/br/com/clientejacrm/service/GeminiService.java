package br.com.clientejacrm.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GeminiService {

    private Client client;

    @PostConstruct
    void init() {
        client = new Client();
    }

    public String generateText(String prompt) {
        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);
        return response.text();
    }
}
