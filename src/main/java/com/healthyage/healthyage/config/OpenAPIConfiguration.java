package com.healthyage.healthyage.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {
    @Value("${openapi.server.url}")
    private String serverUrl;

    @Value("${deployment.environment}")
    private String deploymentEnvironment;    

    @Bean
    OpenAPI defineOpenApi() {
        var server = new Server();
        server.setUrl(serverUrl);
        server.setDescription(deploymentEnvironment);

        var myContact = new Contact();
        myContact.setName("Alan Alexis Aguilar");
        myContact.setEmail("alanalexisaguilar@gmail.com");

        var information = new Info()
                .title("API para el manejo de la información de la aplicación Healty Age")
                .version("1.0")
                .description(
                        "Esta api sirve como intermediario para el procesamiento y almacenamiento de la información de la aplicación Healty Age")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
