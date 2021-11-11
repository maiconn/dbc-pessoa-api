package com.dbc.pessoaapi.repository;

import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {
    List<PessoaEntity> findByNome(String nome);
    PessoaEntity findByCpf(String cpf);
    List<PessoaEntity> findByNomeContainingIgnoreCase(String nome);

    @Query("select p " +
            " from PESSOA p " +
            "where p.cpf = ?1 ")
    PessoaEntity procurarPorCpf(String cpf);

    @Query("select p from PESSOA p " +
            " where p.cpf = :meuCpf " +
            "   and p.nome = :nome ")
    PessoaEntity procurarPorCpfParam(@Param("meuCpf") String cpf, String nome);

    @Query("select p from PESSOA p where p.cpf = :meuCpf")
    PessoaEntity procurarPorCpfParamNomeIgual(String meuCpf);

    @Query("select p " +
            " from PESSOA p " +
            " join p.contatos c ")
    List<PessoaEntity> procurarPessoasComContatos();

    @Query(value = "select * " +
            "         from VEM_SER.PESSOA p " +
            "   inner join VEM_SER.CONTATO c on (p.id_pessoa = c.id_pessoa)", nativeQuery = true)
    List<PessoaEntity> procurarPessoasComContatosNativo();


    @Query("select count(p) " +
            " from PESSOA p " +
            " left join p.contatos c ")
    Long countProcurarPessoasComContatos();

    @Query(value = "select * from VEM_SER.PESSOA where cpf = :cpf", nativeQuery = true)
    PessoaEntity getByCpfNativo(String cpf);

    @Query(value = "select * from VEM_SER.PESSOA where upper(nome) like upper(:nome)", nativeQuery = true)
    List<PessoaEntity> getPorQualquerNome(String nome);


    @Query(value = "select p from PESSOA p where upper(p.nome) like upper(?1)")
    Page<PessoaEntity> findByNomeJPQL(String nome, Pageable pageable);

    @Query(value = "select * " +
            "         from PESSOA" +
            "        where upper(nome) like upper(:nome)",
            countQuery = "select count(*) " +
                    "         from PESSOA" +
                    "        where upper(nome) like upper(:nome)",
            nativeQuery = true)
    Page<PessoaEntity> findByNomeNativo(String nome, Pageable pageable);

}
