package org.example.royaleanalytics.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_decks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_deck_cards",
            joinColumns = @JoinColumn(name = "user_deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @Builder.Default
    private Set<Card> cards = new HashSet<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
