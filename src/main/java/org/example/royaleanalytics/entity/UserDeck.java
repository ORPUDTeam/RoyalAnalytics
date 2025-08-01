package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_decks")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private Boolean status;


    @ManyToMany
    @JoinTable(
            name = "deck_cards",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new HashSet<>();


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "player_tag")
    private String playerTag;

}