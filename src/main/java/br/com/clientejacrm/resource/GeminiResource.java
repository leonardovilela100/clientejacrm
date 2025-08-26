package br.com.clientejacrm.resource;

import br.com.clientejacrm.service.GeminiService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/gemini")
public class GeminiResource {

    @Inject
    GeminiService geminiService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String generate(@QueryParam("prompt") String prompt) {
        if (prompt == null || prompt.isEmpty()) {
            prompt = "Explain how AI works in a few words";
        }
        return geminiService.generateText(prompt);
    }
}
