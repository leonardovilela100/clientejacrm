package br.com.clientejacrm.service;

import br.com.clientejacrm.entity.orm.WhatsappSession;
import br.com.clientejacrm.repository.WhatsappSessionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class WhatsappSessionService {

    @Inject
    WhatsappSessionRepository repository;

    public List<WhatsappSession> listAll() {
        return repository.listAll();
    }

    public WhatsappSession findById(Long id) {
        WhatsappSession session = repository.findById(id);
        if (session == null) {
            throw new NotFoundException();
        }
        return session;
    }

    @Transactional
    public WhatsappSession create(WhatsappSession session) {
        repository.persist(session);
        return session;
    }
}
