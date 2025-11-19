package org.openapitools.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Système de Gestion de Tickets (SBIR), API REST")
                                .description("API REST pour le système de gestion de tickets développé en Java pour le cours 6GEI311, Architecture Logicielle  Auteurs: Il'aina Ratefinanahary et Samuel Brassard, UQAC Automne 2025 ")
                                .contact(
                                        new Contact()
                                                .name("SBIR Team")
                                                .email("iratefinan@etu.uqac.ca")
                                )
                                .license(
                                        new License()
                                                .name("Usage Éducatif Uniquement")
                                                .url("https://github.com/TechSamQC/Lab2-GEI311-SBIR")
                                )
                                .version("4.0.0")
                )
        ;
    }
}