package br.com.clientejacrm.whatsapp;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RegisterClientHeaders;

@Path("/")
@RegisterRestClient(configKey = "whatsapp")
@RegisterClientHeaders(WhatsAppHeadersFactory.class)
public interface WhatsAppClient {

    @POST
    @Path("{phone-id}/messages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void sendMessage(@PathParam("phone-id") String phoneId, MessageRequest request);
}
