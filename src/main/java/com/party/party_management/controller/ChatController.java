package com.party.party_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.party.party_management.model.ChatMessage;
import com.party.party_management.service.ChatService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ws")
@CrossOrigin(origins = "http://localhost:4200") // ou o domínio do frontend
public class ChatController {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // Salvar mensagem no banco de dados (supondo que você tenha um método para isso)
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        
        // Retornar a mensagem salva para os clientes
        return savedMessage;
    }

    // Usuário entrando no chat
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                              SimpMessageHeaderAccessor headerAccessor) {
        // Adicionar username na sessão WebSocket
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
        headerAccessor.getSessionAttributes().put("userId", chatMessage.getSenderId());
        
        // Criar uma mensagem de sistema
        ChatMessage systemMessage = new ChatMessage(
            chatMessage.getSenderName() + " entrou no chat!", 
            "system", 
            "Sistema",
            ChatMessage.MessageType.SYSTEM
        );
        
        // Atualizar contagem de usuários
        chatService.incrementUserCount();
        messagingTemplate.convertAndSend("/topic/userCount", chatService.getConnectedUsersCount());
        
        return systemMessage;
    }

    
    // Indicador de digitação
    @MessageMapping("/chat.typing")
    public void sendTypingIndicator(@Payload Map<String, Object> typingData,
                                   SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        String userName = (String) headerAccessor.getSessionAttributes().get("username");
        boolean isTyping = (Boolean) typingData.get("isTyping");
        
        Map<String, Object> response = Map.of(
            "userId", userId,
            "userName", userName,
            "isTyping", isTyping
        );
        
        messagingTemplate.convertAndSend("/topic/typing", response);
    }

    // REST endpoint para buscar histórico de mensagens
    @GetMapping("/api/chat/messages")
    @ResponseBody
    public List<ChatMessage> getChatHistory(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "50") int size) {
        return chatService.getRecentMessages(page, size);
    }
    
    // REST endpoint para buscar mensagens por período
    @GetMapping("/api/chat/messages/period")
    @ResponseBody
    public List<ChatMessage> getMessagesByPeriod(@RequestParam String startDate,
                                                 @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return chatService.getMessagesByPeriod(start, end);
    }
    
    // REST endpoint para obter estatísticas do chat
    @GetMapping("/api/chat/stats")
    @ResponseBody
    public Map<String, Object> getChatStats() {
        return Map.of(
            "connectedUsers", chatService.getConnectedUsersCount(),
            "totalMessages", chatService.getTotalMessagesCount(),
            "messagesLastHour", chatService.getMessagesLastHour(),
            "activeUsers", chatService.getActiveUsersLast24Hours()
        );
    }
    
    @PostMapping("/api/chat/send")
    @ResponseBody
    public ChatMessage sendMessageViaRest(@RequestBody ChatMessage chatMessage) {
        return chatService.saveMessage(chatMessage);
    }
}
