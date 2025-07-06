package com.party.party_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefixo para mensagens que vão para o broker
        config.enableSimpleBroker("/topic", "/queue");
        // Prefixo para mensagens que vão para o controller
        config.setApplicationDestinationPrefixes("/app");
        // Prefixo para mensagens direcionadas a usuários específicos
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("/ws")
        		.setAllowedOriginPatterns("http://localhost:4200")  // Permite o frontend na porta 4200
                .withSockJS(); // Isso cobre clientes que não suportam WebSocket nativo
    }
}