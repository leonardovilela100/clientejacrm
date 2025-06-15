package br.com.clientejacrm.repository;

import br.com.clientejacrm.entity.orm.WhatsappSession;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WhatsappSessionRepository implements PanacheRepository<WhatsappSession> {
}
