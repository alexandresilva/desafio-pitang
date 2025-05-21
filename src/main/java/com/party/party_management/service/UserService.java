package com.party.party_management.service;

import com.party.party_management.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAllUsers();
    User findById(Long id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
}