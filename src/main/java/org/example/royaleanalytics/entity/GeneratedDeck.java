package org.example.royaleanalytics.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "generated_decks")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_tag", nullable = false)
    private User user;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "generated_deck_cards",  // новая таблица-связка
            joinColumns = @JoinColumn(name = "generated_deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private Set<Card> cards = new HashSet<>();  // Set, чтобы избежать дубликатов


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}