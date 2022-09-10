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

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @GetMapping(value = "/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect(getURL() + "/swagger-ui/index.html");
    }

    private String getURL() {
        if ("hml".equals(activeProfile)) {
            return "/" + appName;
        } else {
            return "";
        }
    }

    @Bean
    public OpenAPI springShopOpenAPI(ServletContext servletContext) {
        String securitySchemeName = "bearerAuth";
        return new OpenAPI().servers(List.of(new Server().url(getURL())))
                .paths(new Paths())
                .info(new Info().title(appName)
                        .description("Documentação " + appName)
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