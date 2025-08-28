package br.com.clientejacrm.service;


import br.com.clientejacrm.entity.enums.StatusUsuario;
import br.com.clientejacrm.entity.orm.Usuario;
import br.com.clientejacrm.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public List<Usuario> listAll() {
        return usuarioRepository.listAll();
    }

    public Usuario findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new NotFoundException();
        }
        return usuario;
    }

    @Transactional
    public Usuario create(Usuario usuario) {
        usuario.setStatus(StatusUsuario.ATIVO);
        usuarioRepository.persist(usuario);
        return usuario;
    }

    @Transactional
    public Usuario update(Long id, Usuario updated) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new NotFoundException();
        }
        usuario.setNome(updated.getNome());
        usuario.setEmail(updated.getEmail());
        usuario.setSenhaHash(updated.getSenhaHash());

        return usuario;
    }

    @Transactional
    public void delete(Long id) {
        boolean deleted = usuarioRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException();
        }
    }
}
