package com.party.party_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Party Management API - Pitang")
						.version("1.0.0")
						.description("API para gerenciamento de eventos e pagamentos")
						.contact(new Contact()
								.name("Suporte")
								.email("suporte@partymanagement.com")));
	}
}
