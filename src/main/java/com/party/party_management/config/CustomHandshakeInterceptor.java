package com.party.party_management.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Map<String, Object> attributes
    ) throws Exception {
        // Exemplo: pegar o userId da URL: ws://localhost/ws?userId=alisson
        String query = request.getURI().getQuery();
        if (query != null && query.contains("userId=")) {
            String[] parts = query.split("userId=");
            String userId = parts[1];
            attributes.put("userId", userId);
            System.out.println("✅ Interceptor: userId recebido = " + userId);
        } else {
            System.out.println("⚠️ Interceptor: Nenhum userId encontrado na query");
        }
        return true;
    }

    @Override
    public void afterHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        @Nullable Exception exception
    ) {}
}
