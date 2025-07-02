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
        logger.info("Nova conexão WebSocket estabelecida");
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
            
            // Decrementar contador de usuários
            chatService.decrementUserCount();
            
            // Criar mensagem de sistema informando saída do usuário
            ChatMessage chatMessage = new ChatMessage(
                username + " saiu do chat!", 
                "system", 
                "Sistema",
                ChatMessage.MessageType.SYSTEM
            );
            
            // Enviar mensagem para todos os clientes
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
            
            // Enviar contagem atualizada para todos os clientes
            messagingTemplate.convertAndSend("/topic/userCount", chatService.getConnectedUsersCount());
        }
    }
}