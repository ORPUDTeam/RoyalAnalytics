package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Check;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tournaments")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "game_mod IN ('Single', 'Duel', 'Clan')")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "interval")
    private Duration duration;

    @Column(name = "game_mod", length = 20)
    private String gameMod;

    @Column(nullable = true)
    private String password;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;
}