package br.com.clientejacrm.resource;

import br.com.clientejacrm.entity.orm.Interacao;
import br.com.clientejacrm.entity.orm.Lead;
import br.com.clientejacrm.service.LeadService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Context;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.net.URI;
import java.util.List;

@Path("/leads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LeadResource {

    @Inject
    LeadService leadService;

    @Inject
    JsonWebToken jwt;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lead> list() {
        return leadService.listAll();
    }

    @GET
    @Path("/{id}")
    public Lead get(@PathParam("id") Long id) {
        return leadService.findById(id);
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Lead lead, @Context UriInfo uriInfo) {
        Lead created = leadService.create(lead);
        URI uri = uriInfo.getAbsolutePathBuilder().path(created.getId().toString()).build();
        return Response.created(uri).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Lead update(@PathParam("id") Long id, Lead updated) {
        return leadService.update(id, updated);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        leadService.delete(id);
    }

    @POST
    @Path("/{id}/interacoes")
    public Interacao addInteracao(@PathParam("id") Long id, Interacao interacao) {
        return leadService.addInteracao(id, interacao);
    }

    @GET
    @Path("/{id}/interacoes")
    public List<Interacao> listInteracoes(@PathParam("id") Long id) {
        return leadService.listInteracoes(id);
    }
}
