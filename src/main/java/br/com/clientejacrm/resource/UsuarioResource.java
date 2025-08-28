package br.com.clientejacrm.resource;

import br.com.clientejacrm.entity.enums.TipoContato;
import br.com.clientejacrm.entity.orm.Cliente;
import br.com.clientejacrm.entity.orm.Usuario;
import br.com.clientejacrm.service.ClienteService;
import br.com.clientejacrm.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    public List<Usuario> list() {
        return usuarioService.listAll();
    }

    @GET
    @Path("/{id}")
    public Usuario get(@PathParam("id") Long id) {
        return usuarioService.findById(id);
    }

    @POST
    public Response create(Usuario usuario, @Context UriInfo uriInfo) {
        Usuario created = usuarioService.create(usuario);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Usuario update(@PathParam("id") Long id, Usuario updated) {
        return usuarioService.update(id, updated);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        usuarioService.delete(id);
    }
}
