package com.dbc.pessoaapi.config;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ForwardedHeaderFilter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Hidden
@Configuration
@RestController
public class OpenApiConfig {

    @GetMapping(value = "/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @Bean
    public OpenAPI springShopOpenAPI(ServletContext servletContext) {
        String securitySchemeName = "bearerAuth";
        Server server = new Server();
        if ("Unknown".equals(activeProfile)) {
            server.setUrl(servletContext.getContextPath());
        } else {
            server.setUrl(servletContext.getContextPath() + "/" + appName);
        }
//        Server server2 = new Server();
//        server2.setUrl("http://vemser-dbc.dbccompany.com.br:39000/dbc-pessoa-api/");
//        server2.setDescription("PRD");
        return new OpenAPI()
                .servers(List.of(server))
                .paths(new Paths())
                .info(new Info().title("Pessoa API")
                        .description("Pessoa API documentação")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }


    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        ForwardedHeaderFilter forwardedHeaderFilter = new ForwardedHeaderFilter();
        return forwardedHeaderFilter;
    }
}