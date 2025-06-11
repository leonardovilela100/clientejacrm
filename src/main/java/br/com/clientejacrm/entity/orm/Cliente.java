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
}
