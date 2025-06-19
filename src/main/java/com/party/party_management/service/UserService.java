package com.party.party_management.service;

import com.party.party_management.model.Role;
import com.party.party_management.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAllUsers();
    User findById(Long id);
    User findByUsername(String username);
    User createUser(User user);
    User updateUser(User user);
    User findByUsername(String username);
    Role findRoleById(Long roleId);
    void deleteUser(Long id);
}