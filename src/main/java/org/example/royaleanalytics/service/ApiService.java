package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.royaleanalytics.client.ClashRoyaleApiClient;
import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.dto.api.CardsApi;
import org.example.royaleanalytics.dto.api.Player;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {
    private final CardService cardService;
    private final ClashRoyaleApiClient client;

    public Player getPlayer(String tag) {
        try {
            ResponseEntity<Player> response = client.getPlayerInfo(tag);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error getting player info from API", e.getMessage());
        }
        return null;
    }

    public List<CardApi> getCards() {
        try {
            ResponseEntity<CardsApi> response = client.getCards();
            return response.getBody().getCards();
        } catch (Exception e) {
            log.error("Error getting cards from API", e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
