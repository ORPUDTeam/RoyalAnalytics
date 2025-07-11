package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByPlayerTag(String playerTag);
    Optional<User> findByPlayerTag(String playerTag);
}
