package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.entity.PessoaEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    private final Configuration configuration;
    @Value("${spring.mail.username}")
    private String remetente;

    public void enviarEmailParaPessoa(PessoaEntity pessoa) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(remetente);
        helper.setTo(pessoa.getEmail());
        helper.setSubject("Cadastro no Sistema");

        Template template = configuration.getTemplate("cadastro.ftl");
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", pessoa.getNome());
        dados.put("id", pessoa.getIdPessoa());
        dados.put("emailSuporte", remetente);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        helper.setText(html, true);

        emailSender.send(mimeMessage);
    }

}
