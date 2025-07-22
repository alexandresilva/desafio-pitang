package com.party.party_management.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ConnectedUserService {
    private final Set<String> connectedUserIds = ConcurrentHashMap.newKeySet();

    public void addUser(String userId) {
        connectedUserIds.add(userId);
    }

    public void removeUser(String userId) {
        connectedUserIds.remove(userId);
    }

    public int getConnectedUserCount() {
        return connectedUserIds.size();
    }
}


