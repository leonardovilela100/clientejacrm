package br.com.clientejacrm.service;

import br.com.clientejacrm.entity.enums.TipoContato;
import br.com.clientejacrm.entity.orm.Contato;
import br.com.clientejacrm.repository.ContatoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ContatoService {

    @Inject
    ContatoRepository contatoRepository;

    public List<Contato> listAll(TipoContato tipo) {
        if (tipo == null) {
            return contatoRepository.listAll();
        }
        return contatoRepository.find("tipo", tipo).list();
    }

    public Contato findById(Long id) {
        Contato contato = contatoRepository.findById(id);
        if (contato == null) {
            throw new NotFoundException();
        }
        return contato;
    }

    @Transactional
    public Contato create(Contato contato) {
        contato.setDataCriacao(LocalDateTime.now());
        contatoRepository.persist(contato);
        return contato;
    }

    @Transactional
    public Contato update(Long id, Contato updated) {
        Contato contato = contatoRepository.findById(id);
        if (contato == null) {
            throw new NotFoundException();
        }
        contato.setNome(updated.getNome());
        contato.setEmail(updated.getEmail());
        contato.setTelefone(updated.getTelefone());
        contato.setTipo(updated.getTipo());
        contato.setCanalPreferencial(updated.getCanalPreferencial());
        return contato;
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = contatoRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }
}
