package com.mediscreen.gateway.repository;

import com.mediscreen.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findAllByUsername(String username);
}
