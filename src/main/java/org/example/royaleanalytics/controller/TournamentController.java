package org.example.royaleanalytics.controller;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.TournamentCreateRequestDto;
import org.example.royaleanalytics.dto.request.TournamentRegisterRequestDto;
import org.example.royaleanalytics.dto.request.TournamentWithParticipantsDto;
import org.example.royaleanalytics.entity.Tournament;
import org.example.royaleanalytics.entity.TournamentRegistration;
import org.example.royaleanalytics.repository.TournamentRepository;
import org.example.royaleanalytics.service.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentRepository tournamentRepository;

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTournament(@RequestBody TournamentCreateRequestDto request) {
        tournamentService.createTournament(request);
    }

    @PostMapping("/{tournamentId}/register")
    public ResponseEntity<?> registerToTournament(
            @PathVariable Long tournamentId,
            @RequestBody TournamentRegisterRequestDto request
    ) {
        tournamentService.registerPlayer(tournamentId, request.getPlayerTag());
        return ResponseEntity.ok("Успешная регистрация");
    }

    @DeleteMapping("/{tournamentId}/unregister")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unregisterFromTournament(@PathVariable Long tournamentId, @RequestParam String playerTag) {
        tournamentService.unregisterPlayer(tournamentId, playerTag);
    }

    @GetMapping("/{id}")
    public TournamentWithParticipantsDto getTournamentDetails(@PathVariable Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));

        List<TournamentRegistration> participants = tournamentService.getTournamentRegistrations(id);

        return new TournamentWithParticipantsDto(tournament, participants);
    }

}

