package org.example.royaleanalytics.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.entity.Tournament;
import org.example.royaleanalytics.entity.TournamentRegistration;

import java.util.List;

@RequiredArgsConstructor
@Data
public class TournamentWithParticipantsDto {
    private Tournament tournament;
    private List<TournamentRegistration> participants;

    public TournamentWithParticipantsDto(Tournament tournament, List<TournamentRegistration> participants) {
        this.tournament = tournament;
        this.participants = participants;
    }
}
