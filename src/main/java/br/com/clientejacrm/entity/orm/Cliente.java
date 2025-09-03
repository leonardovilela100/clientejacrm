package br.com.clientejacrm.entity.orm;

import br.com.clientejacrm.enums.CanalContato;
import br.com.clientejacrm.enums.TipoContato;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
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

    @Column(name = "empresa", length = 100)
    private String empresa;

    @Column(name = "cnpj", length = 20)
    private String cnpj;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


}
