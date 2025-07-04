package com.party.party_management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.party.party_management.model.ChatMessage;
import com.party.party_management.service.ChatService;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ChatService chatService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        // Obtém o username - você pode configurar para obter dinamicamente via login
        String username = "usuario1";  // Exemplo fixo

        headerAccessor.getSessionAttributes().put("username", username);
        
        logger.info("Nova conexão WebSocket estabelecida para o usuário: {}", username);
        chatService.incrementUserCount();
        
        // Enviar contagem atualizada para todos os clientes
        messagingTemplate.convertAndSend("/topic/userCount", chatService.getConnectedUsersCount());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            logger.info("Usuário desconectado: {}", username);
            chatService.decrementUserCount();
            
            // Enviar mensagem de sistema quando alguém sair
            ChatMessage chatMessage = new ChatMessage(username + " saiu do chat!", "system", "Sistema", ChatMessage.MessageType.SYSTEM);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
            
            // Enviar contagem atualizada para todos os clientes
            messagingTemplate.convertAndSend("/topic/userCount", chatService.getConnectedUsersCount());
        }
    }
}
	