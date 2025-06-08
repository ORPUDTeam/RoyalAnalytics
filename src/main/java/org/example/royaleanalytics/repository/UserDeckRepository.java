package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.UserDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeckRepository extends JpaRepository<UserDeck, Integer> {


}
