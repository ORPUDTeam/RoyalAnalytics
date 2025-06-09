package org.example.royaleanalytics.controller;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.CardFilter;
import org.example.royaleanalytics.dto.response.CardResponse;
import org.example.royaleanalytics.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAll(
            @RequestParam("rarity") String rarity,
            @RequestParam("elixir") int elixir,
            @RequestParam("arena") int arena
    ){
        CardFilter filter = new CardFilter();
        filter.setRarity(rarity);
        filter.setElixir(elixir);
        filter.setArena(arena);
        List<CardResponse> cards = cardService.getAll(filter);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(cardService.getById(id));
    }

}