package com.dbc.pessoaapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;


// @Data // @Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
@Getter
@Setter
@Entity(name="PESSOA")
public class PessoaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PESSOA_SEQUENCIA")
    @SequenceGenerator(name = "PESSOA_SEQUENCIA", sequenceName = "seq_pessoa2", allocationSize = 1)
    @Column(name = "id_pessoa")
    private Integer idPessoa;

    @Column(name="nome")
    private String nome; //findByNome

    @Column(name="data_nascimento")
    private LocalDate dataNascimento; //findByDataNascimento

    @Column(name="cpf")
    private String cpf;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "pessoaEntity", fetch = FetchType.LAZY)
    private Set<ContatoEntity> contatos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Pessoa_X_Pessoa_Endereco",
            joinColumns = @JoinColumn(name = "id_pessoa"),
            inverseJoinColumns = @JoinColumn(name = "id_endereco")
    )
    private Set<EnderecoEntity> enderecos;
}