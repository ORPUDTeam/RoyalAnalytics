package org.example.royaleanalytics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.UserDeckResponse;
import org.example.royaleanalytics.dto.request.DeckCreateRequest;
import org.example.royaleanalytics.entity.Card;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.entity.UserDeck;
import org.example.royaleanalytics.exception.CardNotFoundException;
import org.example.royaleanalytics.exception.UserDeckNotFoundException;
import org.example.royaleanalytics.mapper.UserDeckMapper;
import org.example.royaleanalytics.repository.CardRepository;
import org.example.royaleanalytics.repository.UserDeckRepository;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Validated
public class UserDeckService {

    private final UserDeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final UserDeckMapper mapper;
    private final UserService userService;
    private final UserRepository userRepository;

    public List<UserDeckResponse> getAll(Authentication authentication) {
        User user = userService.getUser(authentication);
        return deckRepository.findAll()
                .stream()
                .filter(deck -> deck.getUser().getUsername().equals(user.getUsername()))
                .map(mapper::toResponse)
                .toList();
    }

    public void create(Authentication authentication, @Valid DeckCreateRequest request) {
        UserDeck userDeck = mapper.toUserDeck(request);
        Set<Card> allById = new HashSet<>(cardRepository
                .findAllById(request.getCards()));
        userDeck.setCards(allById);
        userDeck.setCreatedAt(LocalDateTime.now());
        userDeck.setStatus(false);
        User user = userService.getUser(authentication);
        userDeck.setUser(user);
        deckRepository.save(userDeck);
    }

    public void patch(Authentication authentication, int deck_id, @Valid DeckCreateRequest request) {
        UserDeck deck = deckRepository.findById(deck_id)
                .orElseThrow(() -> new UserDeckNotFoundException(deck_id));
        if(deck.getUser().equals(userService.getUser(authentication))) {
            throw new RuntimeException("нельзя изменять чужие колоды");
        }
        patching(deck, request);
        deckRepository.save(deck);
    }

    private void patching(UserDeck userDeck, DeckCreateRequest request) {
        userDeck.setName(request.getName() == null? userDeck.getName() : request.getName());
        for(Integer cardId: request.getCards()) {
            if(cardId == null){
                continue;
            }
            Card byId = cardRepository.findById(cardId)
                    .orElseThrow(() -> new CardNotFoundException(cardId));
            userDeck.getCards().add(byId);
        }
    }

    public UserDeck createReal(List<CardApi> deck, User user){
        return userDeckRepository.save(userDeckMapper.mapToUserDeck(deck, user));
    }

    public UserDeck get(User user){
        return userDeckRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("нет такой деки"));
    }

}
