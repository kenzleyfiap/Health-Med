package br.com.health.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(info())
                .components(components());
    }

    private Components components() {
        Components components = new Components()
                .addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));

        // Define o schema para LocalTime
        Schema<String> localTimeSchema = new Schema<>();
        localTimeSchema.setType("string");
        localTimeSchema.setFormat("time"); // define como um formato de tempo
        components.addSchemas("LocalTime", localTimeSchema);

        return components;
    }

    private Info info() {
        Info info = new Info();
        info.setContact(contact());
        info.setTitle("Health&Med - Agendamento de Consultas");
        info.setVersion("0.0.1");
        return info;
    }

    private Contact contact() {
        Contact contact = new Contact();
        contact.setEmail("kenzleydev@outlook.com");
        contact.setName("Luan Kenzley");
        return contact;
    }
}
