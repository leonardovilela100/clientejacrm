package br.com.clientejacrm.entity.orm;

import br.com.clientejacrm.entity.enums.Origem;
import br.com.clientejacrm.entity.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lead")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false,  length = 50)
    private String nome;

    @Column(name = "email", nullable = false,  length = 50)
    private String email;

    @Column(name = "telefone", nullable = false,  length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "origem")
    private Origem origem;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "proximo_follow_up")
    private LocalDateTime proximoFollowUp;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interacao> interacoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getProximoFollowUp() {
        return proximoFollowUp;
    }

    public void setProximoFollowUp(LocalDateTime proximoFollowUp) {
        this.proximoFollowUp = proximoFollowUp;
    }

    public List<Interacao> getInteracoes() {
        return interacoes;
    }

    public void setInteracoes(List<Interacao> interacoes) {
        this.interacoes = interacoes;
    }
}

