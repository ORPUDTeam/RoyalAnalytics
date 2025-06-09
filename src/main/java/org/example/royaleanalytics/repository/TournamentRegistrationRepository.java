package org.example.royaleanalytics.repository;

import org.example.royaleanalytics.entity.Tournament;
import org.example.royaleanalytics.entity.TournamentRegistration;
import org.example.royaleanalytics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, Long> {
    boolean existsByUserAndTournament(User user, Tournament tournament);
    Optional<TournamentRegistration> findByUserAndTournament(User user, Tournament tournament);
    List<TournamentRegistration> findAllByTournament(Tournament tournament);
}
