package org.example.royaleanalytics.controller;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.UserDeckResponse;
import org.example.royaleanalytics.dto.request.DeckCreateRequest;
import org.example.royaleanalytics.service.UserDeckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userDeckService.getAll(auth));
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> create(DeckCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDeckService.create(authentication, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{deck_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> patch(@PathVariable("deck_id") int deck_id,
                                      @RequestBody DeckCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userDeckService.patch(authentication, deck_id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
