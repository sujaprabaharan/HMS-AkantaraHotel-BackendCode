package com.akantara.AkantaraHotel.repository;

import com.akantara.AkantaraHotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// UserRepository is an interface that acts as a data access layer for the User entity
// It doesn’t need an implementation class — Spring Data JPA automatically generates the implementation at runtime.
public interface UserRepository extends JpaRepository<User, Long> {

    // Finds a user by their email address and returns it as an Optional
    Optional<User> findByEmail(String email);
}
