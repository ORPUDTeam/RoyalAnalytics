package org.example.royaleanalytics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.api.CardApi;
import org.example.royaleanalytics.dto.request.CardFilter;
import org.example.royaleanalytics.dto.response.CardResponse;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.exception.CardNotFoundException;
import org.example.royaleanalytics.exception.NotFoundServiceException;
import org.example.royaleanalytics.mapper.CardMapper;
import org.example.royaleanalytics.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Transactional
    public void processingCardsFromApi(List<CardApi> cardApis){
        List<Card> cards = cardApis.stream()
                .map(cardMapper::mapToCard)
                .toList();
        cardRepository.saveAll(cards);
    }

    public List<CardResponse> getAll(@Valid CardFilter filter) {
        return cardRepository.findAll().stream()
                .filter(card -> filter.getRarity() == null || card.getRarity().equals(filter.getRarity()))
                .filter(card -> filter.getElixir() == null || card.getElixir() == (filter.getElixir()))
                .filter(card -> filter.getArena() == null || card.getArena().equals(filter.getArena()))
                .map(cardMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    public CardResponse getById(int id) {
        return cardMapper.convertToResponse(cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id)));
    }
}
