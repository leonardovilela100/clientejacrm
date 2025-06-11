package br.com.clientejacrm.resource;

import br.com.clientejacrm.entity.Interacao;
import br.com.clientejacrm.entity.Lead;
import br.com.clientejacrm.repository.InteracaoRepository;
import br.com.clientejacrm.repository.LeadRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import jakarta.ws.rs.NotFoundException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Path("/leads")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LeadResource {

    @Inject
    LeadRepository leadRepository;

    @Inject
    InteracaoRepository interacaoRepository;

    @GET
    public List<Lead> list() {
        return leadRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Lead get(@PathParam("id") Long id) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        return lead;
    }

    @POST
    @Transactional
    public Response create(Lead lead, @Context UriInfo uriInfo) {
        lead.setDataCriacao(LocalDateTime.now());
        leadRepository.persist(lead);
        URI uri = uriInfo.getAbsolutePathBuilder().path(lead.getId().toString()).build();
        return Response.created(uri).entity(lead).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Lead update(@PathParam("id") Long id, Lead updated) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        lead.setNome(updated.getNome());
        lead.setEmail(updated.getEmail());
        lead.setTelefone(updated.getTelefone());
        lead.setOrigem(updated.getOrigem());
        lead.setStatus(updated.getStatus());
        lead.setProximoFollowUp(updated.getProximoFollowUp());
        return lead;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        boolean deleted = leadRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }

    @POST
    @Path("/{id}/interacoes")
    @Transactional
    public Interacao addInteracao(@PathParam("id") Long id, Interacao interacao) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        interacao.setLead(lead);
        interacao.setDataHora(LocalDateTime.now());
        interacaoRepository.persist(interacao);
        return interacao;
    }

    @GET
    @Path("/{id}/interacoes")
    public List<Interacao> listInteracoes(@PathParam("id") Long id) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        return lead.getInteracoes();
    }
}
