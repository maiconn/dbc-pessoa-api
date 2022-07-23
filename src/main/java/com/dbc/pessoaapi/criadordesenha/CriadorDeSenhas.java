package com.dbc.pessoaapi.criadordesenha;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CriadorDeSenhas {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senha = bCryptPasswordEncoder.encode("123");
        System.out.println(senha);
        String minhaSenhaCript = "$2a$10$GmzooTT.LrDzaH5U76ktJe20NcgDg0pbUBUuqB./jClx7xLggsu92";
        boolean matches = bCryptPasswordEncoder.matches("123", minhaSenhaCript);
        System.out.println(matches);
    }
}
