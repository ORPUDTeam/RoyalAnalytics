package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.entity.UserDeck;
import org.example.royaleanalytics.mapper.UserCacheMapper;
import org.example.royaleanalytics.repository.UserCacheRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCacheService {
    private final UserCacheRepository userCacheRepository;
    private final PlayerUpdateService playerUpdateService;

    public List<UserCache> getUserCachesToUpdate() {
        return userCacheRepository.findByUpdatedAtLessThan(LocalDateTime.now().minusDays(1))
                .orElse(null);
    }

    @Transactional
    public UserCache getOrCreate(String playerTag) {
        return userCacheRepository.findById(playerTag)
                .orElseGet(() -> {
                    try {
                        return playerUpdateService.create(playerTag);
                    } catch (Exception e) {
                        log.error("Failed to create user cache for tag: {}", playerTag, e);
                        throw new RuntimeException("Failed to create user cache");
                    }
                });
    }

    @Transactional
    public UserCache forceUpdate(String playerTag) {
        return playerUpdateService.forceUpdate(playerTag);
    }
}