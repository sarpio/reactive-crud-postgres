package com.sarpio.edu.r2dbcpostgres.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {


    @Bean
    OpenAPI openAPI() {
        Server server = new Server().url("http://localhost:8080").description("Reactive CRUD Operations");
        Contact contact = new Contact().email("piotr.sarnecki@yahoo.pl").name("Piotr Sarnecki");
        Info info = new Info().title("Reactive REST API").version("v.1.0.0").description("Spring Reactive + OpenApi 3").contact(contact);
        return new OpenAPI().info(info).addServersItem(server);
    }
}
