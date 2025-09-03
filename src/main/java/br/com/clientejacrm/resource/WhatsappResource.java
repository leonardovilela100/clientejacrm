package br.com.clientejacrm.resource;

import br.com.clientejacrm.dto.ContactRequestDto;
import br.com.clientejacrm.dto.MessageRequestDto;
import br.com.clientejacrm.service.WhatsappService;
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
public class WhatsappResource {

    @Inject
    WhatsappService service;

    @POST
    @Path("/send-message")
    public Response sendMessage(MessageRequestDto request) {
        service.sendText(request.getNumber(), request.getMessage());
        return Response.accepted().build();
    }

    @POST
    @Path("/contacts")
    public Response receiveContact(ContactRequestDto contactRequest) {
        //service.receiveContact(contactRequest);
        return  Response.accepted().build();
    }
}
