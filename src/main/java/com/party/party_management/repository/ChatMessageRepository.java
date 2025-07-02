package com.party.party_management.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.party.party_management.model.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    // Buscar mensagens por período ordenadas por timestamp
    List<ChatMessage> findByTimestampBetweenOrderByTimestampAsc(
        LocalDateTime startDate, 
        LocalDateTime endDate
    );
    
    // Buscar mensagens por usuário
    List<ChatMessage> findBySenderIdOrderByTimestampDesc(String senderId, Pageable pageable);
    
    // Buscar mensagens contendo texto específico
    List<ChatMessage> findByContentContainingIgnoreCaseOrderByTimestampDesc(
        String content, 
        Pageable pageable
    );
    
    // Contar mensagens após uma data específica
    long countByTimestampAfter(LocalDateTime timestamp);
    
    // Contar usuários únicos após uma data específica
    @Query("SELECT COUNT(DISTINCT c.senderId) FROM ChatMessage c WHERE c.timestamp > :timestamp")
    long countDistinctSenderIdByTimestampAfter(@Param("timestamp") LocalDateTime timestamp);
    
    // Deletar mensagens antigas
    @Modifying
    @Transactional
    void deleteByTimestampBefore(LocalDateTime timestamp);
    
    // Buscar últimas N mensagens
    @Query("SELECT c FROM ChatMessage c ORDER BY c.timestamp DESC")
    List<ChatMessage> findTopNMessages(Pageable pageable);
    
    @Query("SELECT c FROM ChatMessage c WHERE c.timestamp >= :startOfDay AND c.timestamp < :endOfDay ORDER BY c.timestamp ASC")
    List<ChatMessage> findTodayMessages(
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
    );
    
    // Buscar mensagens por tipo
    List<ChatMessage> findByTypeOrderByTimestampDesc(ChatMessage.MessageType type, Pageable pageable);
    
    // Contar mensagens por usuário
    @Query("SELECT COUNT(c) FROM ChatMessage c WHERE c.senderId = :senderId")
    long countMessagesBySenderId(@Param("senderId") String senderId);
    
    // Buscar usuários mais ativos
    @Query("SELECT c.senderId, c.senderName, COUNT(c) as messageCount " +
           "FROM ChatMessage c " +
           "WHERE c.timestamp > :startDate " +
           "GROUP BY c.senderId, c.senderName " +
           "ORDER BY messageCount DESC")
    List<Object[]> findMostActiveUsers(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}
