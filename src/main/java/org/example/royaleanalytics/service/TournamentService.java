package org.example.royaleanalytics.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.TournamentCreateRequestDto;
import org.example.royaleanalytics.entity.Tournament;
import org.example.royaleanalytics.entity.TournamentRegistration;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.repository.TournamentRegistrationRepository;
import org.example.royaleanalytics.repository.TournamentRepository;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final TournamentRegistrationRepository registrationRepository;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public void createTournament(TournamentCreateRequestDto request) {

        List<String> allowedModes = List.of("Single", "Duel", "Clan");
        if (!allowedModes.contains(request.getGameMod())) {
            throw new IllegalArgumentException("Unknown game mode");
        }

        Tournament tournament = new Tournament()
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setDuration(request.getDuration())
                .setGameMod(request.getGameMod())
                .setPassword(request.getPassword())
                .setStartTime(request.getStartTime());
        tournamentRepository.save(tournament);
    }

    public void registerPlayer(Long tournamentId, String playerTag) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
        User player = userRepository.findByPlayerTag(playerTag)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        if (registrationRepository.existsByUserAndTournament(player, tournament)) {
            throw new IllegalStateException("Already registered");
        }

        TournamentRegistration participant = new TournamentRegistration();
        participant.setTournament(tournament);
        participant.setUser(player);
        participant.setRegistrationAt(LocalDateTime.now());
        registrationRepository.save(participant);
    }

    public void unregisterPlayer(Long tournamentId, String playerTag) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
        User player = userRepository.findByPlayerTag(playerTag)
                .orElseThrow(() -> new EntityNotFoundException("Player not found"));

        TournamentRegistration participant = registrationRepository.findByUserAndTournament(player, tournament)
                .orElseThrow(() -> new EntityNotFoundException("Registration not found"));

        registrationRepository.delete(participant);
    }

    public List<TournamentRegistration> getTournamentRegistrations(Long tournamentId) {
        return registrationRepository.findAllByTournament(tournamentRepository.findById(tournamentId).orElseThrow(() -> new EntityNotFoundException("Tournament not found")));
    }
}

