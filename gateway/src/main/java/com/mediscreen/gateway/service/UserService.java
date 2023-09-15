package com.mediscreen.gateway.service;

import com.mediscreen.gateway.model.User;
import com.mediscreen.gateway.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User create(String username, String password) {

        if (find(username) != null) {
            log.error("User already exists with username: {}", username);
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User find(String username)   {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Mono<User> findByUsername(String username) {
        return Mono.justOrEmpty(userRepository.findByUsername(username));
    }

    @Transactional(readOnly = true)
    public boolean passwordIsValid(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional(readOnly = true)
    public List<UserDetails> findAllByUsername(String username)   {
        return userRepository.findAllByUsername(username);
    }
}