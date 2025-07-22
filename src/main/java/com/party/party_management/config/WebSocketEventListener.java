package com.party.party_management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.party.party_management.model.ChatMessage;
import com.party.party_management.service.ChatService;
import com.party.party_management.service.ConnectedUserService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ConnectedUserService connectedUserService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        List<String> userIdHeader = accessor.getNativeHeader("userId");
        List<String> userNameHeader = accessor.getNativeHeader("userName");

        String userId = userIdHeader != null && !userIdHeader.isEmpty() ? userIdHeader.get(0) : null;
        String userName = userNameHeader != null && !userNameHeader.isEmpty() ? userNameHeader.get(0) : null;

        if (accessor.getSessionAttributes() != null) {
            accessor.getSessionAttributes().put("userId", userId);
            accessor.getSessionAttributes().put("username", userName);
        }

        // Salvar userId na sua ConnectedUserService
        if (userId != null) {
            connectedUserService.addUser(userId);
        }

        // Enviar nova contagem para todos
        messagingTemplate.convertAndSend("/topic/userCount", connectedUserService.getConnectedUserCount());
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        String username = (String) sessionAttributes.get("username");
        String userId = (String) sessionAttributes.get("userId");

        if (userId != null) {
            logger.info("Removendo usuário com ID: {}", userId);
            connectedUserService.removeUser(userId);
        }

        if (username != null) {
            logger.info("Usuário desconectado: {}", username);
            ChatMessage chatMessage = new ChatMessage(
                username + " saiu do chat!",
                "system",
                "Sistema",
                ChatMessage.MessageType.SYSTEM
            );
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }

        // Enviar contagem atualizada
        messagingTemplate.convertAndSend("/topic/userCount", connectedUserService.getConnectedUserCount());
        logger.info("Usuários conectados após desconexão: {}", connectedUserService.getConnectedUserCount());
    }




}
