package com.mediscreen.gateway.service;

import com.mediscreen.gateway.model.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

public interface UserService {

    /**
     * Creates a new user and persists it in the database
     * @param username the name of the user
     * @param password the raw password chosen by the user
     * @return the User object that has been persisted
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    User create(String username, String password);

    /**
     * Finds a user in the database
     * @param username the name of the user to look for
     * @return the User object that has been found
     */
    @Transactional(readOnly = true)
    User find(String username);

    /**
     * Finds a user in the database and returns it in the form of a Mono<User> object
     * (a type common in 'reactive' programming) which is used by the Spring Security
     * to manage authentication
     * @param username the name of the user to authenticate
     * @return an object that can 'emit' the proper User object once it is available
     */
    @Transactional(readOnly = true)
    Mono<User> findByUsername(String username);
}
