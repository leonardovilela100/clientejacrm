package br.com.clientejacrm.service;

import br.com.clientejacrm.entity.orm.Interacao;
import br.com.clientejacrm.entity.orm.Lead;
import br.com.clientejacrm.repository.InteracaoRepository;
import br.com.clientejacrm.repository.LeadRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class LeadService {

    @Inject
    LeadRepository leadRepository;

    @Inject
    InteracaoRepository interacaoRepository;

    public List<Lead> listAll() {
        return leadRepository.listAll();
    }

    public Lead findById(Long id) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        return lead;
    }

    @Transactional
    public Lead create(Lead lead) {
        lead.setDataCriacao(LocalDateTime.now());
        lead.setProximoFollowUp(LocalDateTime.now());
        leadRepository.persist(lead);
        return lead;
    }

    @Transactional
    public Lead update(Long id, Lead updated) {
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

    @Transactional
    public void delete(Long id) {
        boolean deleted = leadRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }

    @Transactional
    public Interacao addInteracao(Long id, Interacao interacao) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        interacao.setLead(lead);
        interacao.setDataHora(LocalDateTime.now());
        interacaoRepository.persist(interacao);
        return interacao;
    }

    public List<Interacao> listInteracoes(Long id) {
        Lead lead = leadRepository.findById(id);
        if (lead == null) {
            throw new NotFoundException();
        }
        return lead.getInteracoes();
    }
}
