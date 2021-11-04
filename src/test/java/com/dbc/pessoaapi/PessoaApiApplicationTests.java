package com.dbc.pessoaapi;

import com.dbc.pessoaapi.service.EmailServiceAula;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.io.IOException;

@SpringBootTest
class PessoaApiApplicationTests {

	@Autowired
	private EmailServiceAula emailServiceAula;

	@Test
	void contextLoads() throws MessagingException, TemplateException, IOException {
//		emailService.enviarEmailSimples();
//		emailService.enviarEmailComAnexos();
//		emailService.enviarEmailComTemplate();
		emailServiceAula.enviarEmailComTemplateAndAnexos();
	}

}
