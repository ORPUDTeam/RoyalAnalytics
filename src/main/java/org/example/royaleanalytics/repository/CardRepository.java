package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query("""
            SELECT c FROM Card c
            WHERE c.name IN :names
            """)
    List<Card> findAllByNames(@Param("names") List<String> names);


    Optional<Card> findByName(String name);
}
