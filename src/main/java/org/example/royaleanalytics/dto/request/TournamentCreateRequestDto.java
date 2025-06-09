package org.example.royaleanalytics.dto.request;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class TournamentCreateRequestDto {
    private String name;
    private String description;
    private Duration duration;
    private String gameMod;
    private String password;
    private LocalDateTime startTime;
}
