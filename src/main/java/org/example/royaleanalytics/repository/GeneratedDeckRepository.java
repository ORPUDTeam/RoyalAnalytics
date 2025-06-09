package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.GeneratedDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedDeckRepository extends JpaRepository<GeneratedDeck, Long> {
}