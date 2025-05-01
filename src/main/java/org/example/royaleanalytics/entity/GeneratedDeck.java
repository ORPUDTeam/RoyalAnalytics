package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "generated_decks")
public class GeneratedDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 255)
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "generated_deck_cards",
            joinColumns = @JoinColumn(name = "generated_deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // getters and setters
}