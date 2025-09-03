package br.com.clientejacrm.resource;

import br.com.clientejacrm.enums.TipoContato;
import br.com.clientejacrm.entity.orm.Cliente;
import br.com.clientejacrm.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public List<Cliente> list(@QueryParam("tipo") TipoContato tipo) {
        return clienteService.listAll(tipo);
    }

    @GET
    @Path("/{id}")
    public Cliente get(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

    @POST
    public Response create(Cliente cliente, @Context UriInfo uriInfo) {
        Cliente created = clienteService.create(cliente);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Cliente update(@PathParam("id") Long id, Cliente updated) {
        return clienteService.update(id, updated);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        clienteService.delete(id);
    }
}
