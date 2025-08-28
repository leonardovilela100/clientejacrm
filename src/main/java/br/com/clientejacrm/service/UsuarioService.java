package br.com.clientejacrm.service;


import br.com.clientejacrm.entity.enums.StatusUsuario;
import br.com.clientejacrm.entity.orm.Usuario;
import br.com.clientejacrm.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Usuario authenticate(String email, String senha) {
        Usuario usuario = findByEmail(email);
        if (usuario == null) {
            return null;
        }
        String hash = Base64.getEncoder().encodeToString(senha.getBytes(StandardCharsets.UTF_8));
        return hash.equals(usuario.getSenhaHash()) ? usuario : null;
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
        if (updated.getSenha() != null) {
            usuario.setSenha(updated.getSenha());
        }
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
