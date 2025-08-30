package com.stelios.ServiceLink.repository;

import com.stelios.ServiceLink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address. Spring Data JPA automatically
     * implements this method based on its name.
     * @param email The email to search for.
     * @return An Optional containing the User if found.
     */
    Optional<User> findByEmail(String email);
}