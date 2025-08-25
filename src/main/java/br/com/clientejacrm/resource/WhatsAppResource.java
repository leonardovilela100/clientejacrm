package br.com.clientejacrm.resource;

import br.com.clientejacrm.whatsapp.MessageRequest;
import br.com.clientejacrm.whatsapp.WhatsAppService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/whatsapp")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WhatsAppResource {

    @Inject
    WhatsAppService service;

    @POST
    @Path("/send-message")
    public Response sendMessage(MessageRequest request) {
        service.sendText(request.getNumber(), request.getMessage());
        return Response.accepted().build();
    }
}
