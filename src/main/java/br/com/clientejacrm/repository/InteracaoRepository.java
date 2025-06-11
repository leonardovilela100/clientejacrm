package br.com.clientejacrm.repository;

import br.com.clientejacrm.entity.orm.Interacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InteracaoRepository implements PanacheRepository<Interacao> {
}
