package br.com.clientejacrm.resource;

import br.com.clientejacrm.entity.orm.WhatsappSession;
import br.com.clientejacrm.service.WhatsappSessionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/whatsapp-sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WhatsappSessionResource {

    @Inject
    WhatsappSessionService service;

    @GET
    public List<WhatsappSession> list() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public WhatsappSession get(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(WhatsappSession session, @Context UriInfo uriInfo) {
        WhatsappSession created = service.create(session);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }
}
