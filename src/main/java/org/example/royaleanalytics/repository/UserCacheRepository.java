package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.UserCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserCacheRepository extends JpaRepository<UserCache, String> {
    Optional<List<UserCache>> findByUpdatedAtLessThan(LocalDateTime updatedAt);
}
