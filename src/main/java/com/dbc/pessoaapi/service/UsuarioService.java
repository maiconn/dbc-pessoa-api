package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.dto.login.LoginCreateDTO;
import com.dbc.pessoaapi.dto.login.LoginDTO;
import com.dbc.pessoaapi.entity.UsuarioEntity;
import com.dbc.pessoaapi.exception.RegraDeNegocioException;
import com.dbc.pessoaapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findById(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public LoginDTO create(LoginCreateDTO loginDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(loginDTO, UsuarioEntity.class);
        try {
            usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(loginDTO.getSenha()));
            UsuarioEntity save = usuarioRepository.save(usuarioEntity);
            return objectMapper.convertValue(save, LoginDTO.class);
        } catch (Exception ex) {
            throw new RegraDeNegocioException("Login já existente");
        }
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
