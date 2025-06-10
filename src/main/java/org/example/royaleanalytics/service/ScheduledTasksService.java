package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.entity.UserCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledTasksService {
    private final ApiService apiService;
    private final PlayerUpdateService playerUpdateService;
    private final CardService cardService;
    private final UserCacheService userCacheService;

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void updateCards() {
        try {
            List<CardApi> cards = apiService.getCards();
            cardService.processingCardsFromApi(cards);
        } catch (Exception e) {
            log.error("Error updating cards", e);
        }
    }

    @Scheduled(fixedDelay = 3600000 * 24)
    public void dailyUpdatePlayers() {
        List<UserCache> caches = userCacheService.getUserCachesToUpdate();
        if (caches != null) {
            caches.forEach(userCache -> {
                Player updatedPlayer = apiService.getPlayer(userCache.getPlayer_tag());
                playerUpdateService.updatePlayer(userCache, updatedPlayer);
            });
        }
    }
}