package com.mediscreen.gateway.repository;

import com.mediscreen.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    List<UserDetails> findAllByUsername(String username);
}
