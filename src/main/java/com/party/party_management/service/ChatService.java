package com.party.party_management.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.party.party_management.model.ChatMessage;
import com.party.party_management.repository.ChatMessageRepository;

@Service
public class ChatService {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    // Contador de usuários conectados
    private final AtomicInteger connectedUsers = new AtomicInteger(0);
    
    // Salvar mensagem no banco de dados
    public ChatMessage saveMessage(ChatMessage message) {
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        return chatMessageRepository.save(message);
    }
    
    public List<ChatMessage> getMessagesFromToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return chatMessageRepository.findTodayMessages(start, end);
    }
    
    // Buscar mensagens recentes com paginação
    public List<ChatMessage> getRecentMessages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        List<ChatMessage> messages = chatMessageRepository.findAll(pageable).getContent();
        // Reverter a ordem para mostrar mensagens mais antigas primeiro
        return messages.stream()
                .sorted((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()))
                .toList();
    }
    
    // Buscar mensagens por período
    public List<ChatMessage> getMessagesByPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return chatMessageRepository.findByTimestampBetweenOrderByTimestampAsc(startDate, endDate);
    }
    
    // Buscar mensagens por usuário
    public List<ChatMessage> getMessagesByUser(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return chatMessageRepository.findBySenderIdOrderByTimestampDesc(userId, pageable);
    }
    
    // Buscar mensagens contendo texto específico
    public List<ChatMessage> searchMessages(String searchText, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        return chatMessageRepository.findByContentContainingIgnoreCaseOrderByTimestampDesc(searchText, pageable);
    }
    
    // Contar total de mensagens
    public long getTotalMessagesCount() {
        return chatMessageRepository.count();
    }
    
    // Contar mensagens da última hora
    public long getMessagesLastHour() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return chatMessageRepository.countByTimestampAfter(oneHourAgo);
    }
    
    // Contar usuários ativos nas últimas 24 horas
    public long getActiveUsersLast24Hours() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        return chatMessageRepository.countDistinctSenderIdByTimestampAfter(twentyFourHoursAgo);
    }
    
    // Gerenciar contagem de usuários conectados
    public void incrementUserCount() {
        connectedUsers.incrementAndGet();
    }
    
    public void decrementUserCount() {
        connectedUsers.decrementAndGet();
    }
    
    public int getConnectedUsersCount() {
        return connectedUsers.get();
    }
    
    // Limpar mensagens antigas (manter apenas últimos 30 dias)
    public void cleanOldMessages() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        chatMessageRepository.deleteByTimestampBefore(thirtyDaysAgo);
    }
    
    // Buscar últimas mensagens por usuário específico
    public List<ChatMessage> getLastMessagesByUser(String userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("timestamp").descending());
        return chatMessageRepository.findBySenderIdOrderByTimestampDesc(userId, pageable);
    }
}
