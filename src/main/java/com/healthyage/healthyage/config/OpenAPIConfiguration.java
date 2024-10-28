package com.healthyage.healthyage.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Alan Alexis Aguilar");
        myContact.setEmail("alanalexisaguilar@gmail.com");

        Info information = new Info()
                .title("API para el manejo de la información de la aplicación Healty Age")
                .version("1.0")
                .description("Esta api sirve como intermediario para el procesamiento y almacenamiento de la información de la aplicación Healty Age")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
