package br.com.clientejacrm.entity.orm;

import br.com.clientejacrm.entity.enums.CanalContato;
import br.com.clientejacrm.entity.enums.TipoContato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoContato tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal_preferencial")
    private CanalContato canalPreferencial;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "ultimo_contato")
    private LocalDateTime ultimoContato;

    @Column(name = "ultima_compra")
    private LocalDateTime ultimaCompra;

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

    public TipoContato getTipo() {
        return tipo;
    }

    public void setTipo(TipoContato tipo) {
        this.tipo = tipo;
    }

    public CanalContato getCanalPreferencial() {
        return canalPreferencial;
    }

    public void setCanalPreferencial(CanalContato canalPreferencial) {
        this.canalPreferencial = canalPreferencial;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getUltimoContato() {
        return ultimoContato;
    }

    public void setUltimoContato(LocalDateTime ultimoContato) {
        this.ultimoContato = ultimoContato;
    }

    public LocalDateTime getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(LocalDateTime ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }
}
