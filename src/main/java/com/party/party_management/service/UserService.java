package com.party.party_management.service;

import com.party.party_management.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User findById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}