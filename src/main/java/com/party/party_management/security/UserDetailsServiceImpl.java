package com.party.party_management.security;

import com.party.party_management.model.User;
import com.party.party_management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        logger.info("Buscando usuário com username/email: {}", usernameOrEmail);

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado com: {}", usernameOrEmail);
                    return new UsernameNotFoundException("Credenciais inválidas");
                });

        if (!user.getPassword().startsWith("$2a$")) {
            throw new BadCredentialsException("Formato de senha inválido");
        }

        logger.info("Usuário encontrado: {}", user.getUsername());
        return UserDetailsImpl.build(user);
    }
}
