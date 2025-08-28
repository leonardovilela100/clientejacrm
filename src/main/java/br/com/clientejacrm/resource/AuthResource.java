package br.com.clientejacrm.resource;


import br.com.clientejacrm.entity.orm.Usuario;
import br.com.clientejacrm.service.TokenService;
import br.com.clientejacrm.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenService tokenService;

    public static class LoginRequest {
        public String email;
        public String senha;
    }

    public static class TokenResponse {
        public String token;

        public TokenResponse(String token) {
            this.token = token;
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        Usuario usuario = usuarioService.authenticate(request.email, request.senha);
        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String token = tokenService.generateToken(usuario);
        return Response.ok(new TokenResponse(token)).build();
    }

}
