package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.mapper.CardMapper;
import org.example.royaleanalytics.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public void processingCardsFromApi(List<CardApi> cardApis){
        List<Card> cards = cardApis.stream()
                .map(cardMapper::mapToCard)
                .toList();
        //TODO охранение либо изменение
    }

}
