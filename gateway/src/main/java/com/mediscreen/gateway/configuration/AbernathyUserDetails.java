package com.mediscreen.gateway.configuration;

import com.mediscreen.gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbernathyUserDetails implements ReactiveUserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String finalUsername;
        String password;
        List<GrantedAuthority> authorities;
        List<com.mediscreen.gateway.model.User> users = userRepository.findAllByUsername(username);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        } else {
            finalUsername = username;
            password = users.get(0).getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        return Mono.just(new User(finalUsername, password, authorities));
    }
}
