package org.example.royaleanalytics.service;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.GeneratedDeckRequest;
import org.example.royaleanalytics.dto.request.RecommendationRequest;
import org.example.royaleanalytics.dto.response.GeneratedDeckResponse;
import org.example.royaleanalytics.dto.response.RecommendationResponse;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.entity.GeneratedDeck;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.exception.ServiceException;
import org.example.royaleanalytics.mapper.GeneratedDeckMapper;
import org.example.royaleanalytics.openFeign.GeneratedDeckClient;
import org.example.royaleanalytics.repository.CardRepository;
import org.example.royaleanalytics.repository.GeneratedDeckRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Validated
public class GeneratedDeckService {

    private final GeneratedDeckMapper mapper;
    private final GeneratedDeckRepository repository;
    private final GeneratedDeckClient client;
    private final CardRepository cardRepository;
    private final UserService userService;

    public List<GeneratedDeckResponse> getAll(Authentication authentication) {
        User user = userService.getUser(authentication);
        return repository.findAll()
                .stream()
                .filter(generatedDeck -> generatedDeck.getUser().equals(user))
                .map(mapper::toGeneratedDeckResponse)
                .toList();
    }

    public GeneratedDeckResponse generate(Authentication authentication, @Valid GeneratedDeckRequest generatedDeckRequest) {
        try {
            RecommendationResponse recommendations = client.getRecommendations(new RecommendationRequest(generatedDeckRequest.getRating(), 1));
            for(List<String> decks: recommendations.getDecks()){
                Set<Card> cards = new HashSet<>(cardRepository.findAllByNames(decks));
                GeneratedDeck generatedDeck = new GeneratedDeck();
                generatedDeck.setCards(cards);
                generatedDeck.setStatus(true);
                generatedDeck.setDescription("generated deck");
                generatedDeck.setUser(userService.getUser(authentication));
                GeneratedDeck save = repository.save(generatedDeck);
                return mapper.toGeneratedDeckResponse(save);
            }
        } catch (FeignException.FeignClientException e) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return null;
    }
}