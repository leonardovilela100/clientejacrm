package br.com.clientejacrm.repository;

import br.com.clientejacrm.entity.orm.Contato;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContatoRepository implements PanacheRepository<Contato> {
}
