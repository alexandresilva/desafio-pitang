package com.party.party_management.service;

import com.party.party_management.enumerate.UserStatus;
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
    Role findRoleById(Long roleId);
    User updateStatus(Long userId, UserStatus status); 
    void deleteUser(Long id);
}