package br.com.clientejacrm.repository;

import br.com.clientejacrm.entity.orm.Lead;
import br.com.clientejacrm.entity.orm.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LeadRepository implements PanacheRepository<Lead> {

    public Lead findByTelefone(String telefone, Usuario usuario) {
        return find("telefone = ?1 and usuario = ?2", telefone, usuario).firstResult();
    }
}
