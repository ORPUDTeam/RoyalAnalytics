package org.example.royaleanalytics.controller;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.GeneratedDeckRequest;
import org.example.royaleanalytics.dto.response.GeneratedDeckResponse;
import org.example.royaleanalytics.service.GeneratedDeckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GeneratedDeckController {

    private final GeneratedDeckService generatedDeckService;

    @GetMapping("/generated")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GeneratedDeckResponse>> getGeneratedDecks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(generatedDeckService.getAll(auth));
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GeneratedDeckResponse> generate(GeneratedDeckRequest generatedDeckRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GeneratedDeckResponse response = generatedDeckService.generate(authentication,generatedDeckRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(response);
    }
}
