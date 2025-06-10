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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Transactional
    public void processingCardsFromApi(List<CardApi> cardApis){
        cardApis.forEach(cardApi -> {
            Optional<Card> oldCard = cardRepository.findByName(cardApi.getName());
            if (oldCard.isPresent()){
                cardRepository.save(cardMapper.updateCard(oldCard.get(), cardApi));
            } else{
                cardRepository.save(cardMapper.mapToCard(cardApi));
            }
        });
    }

    public List<CardResponse> getAll(@Valid CardFilter filter) {
        return cardRepository.findAll().stream()
                .filter(card -> filter.getRarity() == null || card.getRarity().equals(filter.getRarity()))
                .filter(card -> filter.getElixir() == null || card.getElixir() == (filter.getElixir()))
                .map(cardMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    public CardResponse getById(int id) {
        return cardMapper.convertToResponse(cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id)));
    }

    public Card findByName(String name){
        return cardRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("нет такой карты"));
    }
}
