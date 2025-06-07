package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.RatingCache;
import org.example.royaleanalytics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingCacheRepository extends JpaRepository<RatingCache, Integer> {
    Optional<RatingCache> findByUser(User user);
}
