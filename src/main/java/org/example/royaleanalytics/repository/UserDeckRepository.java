package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.entity.UserDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDeckRepository extends JpaRepository<UserDeck, Integer> {

    Optional<UserDeck> findByPlayerTagAndStatus(String tag, boolean status);

}
