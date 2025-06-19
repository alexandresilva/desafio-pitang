package com.party.party_management.service.impl;

import com.party.party_management.exception.ResourceNotFoundException;
import com.party.party_management.model.Role;
import com.party.party_management.model.User;
import com.party.party_management.repository.RoleRepository;
import com.party.party_management.repository.UserRepository;
import com.party.party_management.security.UserDetailsImpl;
import com.party.party_management.service.UserService;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	/*
	 * // Injeção via construtor (recomendado) public UserServiceImpl(UserRepository
	 * userRepository) { this.userRepository = userRepository; }
	 */

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário com id [" + id + "] não encontrado : "));
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsernameOrEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado! "));
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	@Override
	public User findByUsername(String username) {
	    return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		userRepository.delete(user);
	}

	@Override
	public Role findRoleById(Long id) {
	    return roleRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Role com id [" + id + "] não encontrada"));
	}

}