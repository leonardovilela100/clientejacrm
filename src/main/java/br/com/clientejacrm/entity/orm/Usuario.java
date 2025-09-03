package br.com.clientejacrm.entity.orm;

import br.com.clientejacrm.enums.StatusUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",  nullable = false)
    private String nome;

    @Column(name = "email",  nullable = false, unique = true)
    private String email;

    @Column(name = "telefone", length = 30)
    private String telefone;

    @Column(name = "email_verificado")
    private boolean emailVerificado;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String senha;

    @JsonIgnore
    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusUsuario status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public void setSenha(String senha) {
        this.senha = senha;
        if (senha != null) {
            this.senhaHash = Base64.getEncoder().encodeToString(
                    senha.getBytes(StandardCharsets.UTF_8));
        }
    }

}
