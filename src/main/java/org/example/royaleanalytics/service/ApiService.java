package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.royaleanalytics.client.ClashRoyaleApiClient;
import org.example.royaleanalytics.dto.api.CardApi;
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

    @Scheduled(fixedDelay = 3600000 * 24 * 30)
    public void updateCards(){
        try {
            ResponseEntity<List<CardApi>> response = client.getCards();
            cardService.processingCardsFromApi(response.getBody());
        } catch (Exception e){
            log.error("проблема с получением карт с апи");
        }
    }



}
