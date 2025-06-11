package br.com.clientejacrm.resource;

import br.com.clientejacrm.entity.enums.TipoContato;
import br.com.clientejacrm.entity.orm.Contato;
import br.com.clientejacrm.service.ContatoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/contatos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContatoResource {

    @Inject
    ContatoService contatoService;

    @GET
    public List<Contato> list(@QueryParam("tipo") TipoContato tipo) {
        return contatoService.listAll(tipo);
    }

    @GET
    @Path("/{id}")
    public Contato get(@PathParam("id") Long id) {
        return contatoService.findById(id);
    }

    @POST
    public Response create(Contato contato, @Context UriInfo uriInfo) {
        Contato created = contatoService.create(contato);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Contato update(@PathParam("id") Long id, Contato updated) {
        return contatoService.update(id, updated);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        contatoService.delete(id);
    }
}
