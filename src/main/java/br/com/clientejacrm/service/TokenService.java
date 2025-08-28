package br.com.clientejacrm.service;

import br.com.clientejacrm.entity.orm.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class TokenService {

    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        return Jwt.subject(usuario.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofHours(24)))
                .sign();

    }



}
