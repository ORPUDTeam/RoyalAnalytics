package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.royaleanalytics.client.ClashRoyaleApiClient;
import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.dto.api.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final CardService cardService;
    private final ClashRoyaleApiClient client;
    private final UserCacheService userCacheService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateCards(){
        try {
            ResponseEntity<List<CardApi>> response = client.getCards();
            cardService.processingCardsFromApi(response.getBody());
        } catch (Exception e){
            log.error("проблема с получением карт из апи", e.getMessage());
        }
    }

    public Player getPlayer(String tag){
        try {
            ResponseEntity<Player> response = client.getPlayerInfo(tag);
            return response.getBody();
        } catch (Exception e){
            log.error("проблема с получением информации об игроке из апи", e.getMessage());
        }
        return null;
    }


    @Scheduled(fixedDelay = 3600000 * 24)
    public void dailyUpdatePlayers(){
        userCacheService.getUserCachesToUpdate().forEach(userCache -> {
            String tag = userCache.getPlayer_tag();
            Player updatedPlayer = getPlayer(tag);
            userCacheService.updatePlayer(userCache, updatedPlayer);
        });
    }
}
