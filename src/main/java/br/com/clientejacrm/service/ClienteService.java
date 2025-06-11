package br.com.clientejacrm.service;

import br.com.clientejacrm.entity.enums.TipoContato;
import br.com.clientejacrm.entity.orm.Cliente;
import br.com.clientejacrm.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    public List<Cliente> listAll(TipoContato tipo) {
        if (tipo == null) {
            return clienteRepository.listAll();
        }
        return clienteRepository.find("tipo", tipo).list();
    }

    public Cliente findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException();
        }
        return cliente;
    }

    @Transactional
    public Cliente create(Cliente cliente) {
        cliente.setDataCriacao(LocalDateTime.now());
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente update(Long id, Cliente updated) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException();
        }
        cliente.setNome(updated.getNome());
        cliente.setEmail(updated.getEmail());
        cliente.setTelefone(updated.getTelefone());
        cliente.setTipo(updated.getTipo());
        cliente.setCanalPreferencial(updated.getCanalPreferencial());
        return cliente;
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = clienteRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }
}
