package br.com.clientejacrm.resource;

import br.com.clientejacrm.service.OpenRouterService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Path("/openrouter")
public class OpenRouterResource {

    @Inject
    OpenRouterService openRouterService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String generate(@QueryParam("prompt") String prompt, @Context UriInfo uriInfo) {
        if (prompt == null || prompt.isEmpty()) {
            String rawQuery = uriInfo.getRequestUri().getRawQuery();
            if (rawQuery != null && !rawQuery.isEmpty() && !rawQuery.contains("=")) {
                prompt = URLDecoder.decode(rawQuery, StandardCharsets.UTF_8);
            }
        }
        if (prompt == null || prompt.isEmpty()) {
            prompt = "Explain how AI works in a few words";
        }
        return openRouterService.generateText(prompt);
    }
}
