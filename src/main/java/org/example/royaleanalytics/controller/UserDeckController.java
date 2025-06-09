package org.example.royaleanalytics.controller;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.UserDeckResponse;
import org.example.royaleanalytics.dto.request.DeckCreateRequest;
import org.example.royaleanalytics.service.UserDeckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/decks")
public class UserDeckController {

    private final UserDeckService userDeckService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDeckResponse>> getDecks() {
        return ResponseEntity.ok(userDeckService.getAll());
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(Authentication authentication, DeckCreateRequest request) {
        userDeckService.create(authentication,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{deck_id}")
    public ResponseEntity<Void> patch(@PathVariable("deck_id") int deck_id,@RequestBody DeckCreateRequest request) {
        userDeckService.patch(deck_id,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
