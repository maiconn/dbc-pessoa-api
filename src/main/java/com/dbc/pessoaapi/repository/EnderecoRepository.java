package com.dbc.pessoaapi.repository;

import com.dbc.pessoaapi.entity.EnderecoEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Integer> {

    @Query(" SELECT e " +
            " from ENDERECO_PESSOA e " +
            " WHERE UPPER(e.pais) = UPPER(:pais)")
    List<EnderecoEntity> listEnderecoByPais(@Param("pais") String pais);

    @Query(" SELECT e " +
            " FROM ENDERECO_PESSOA e " +
            " JOIN e.pessoas p " +
            " WHERE p.idPessoa = :idPessoa")
    List<EnderecoEntity> listEnderecoByIdPessoa(@Param("idPessoa") Integer idPessoa);
}
