package br.com.clientejacrm.repository;

import br.com.clientejacrm.entity.orm.Lead;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LeadRepository implements PanacheRepository<Lead> {
}
