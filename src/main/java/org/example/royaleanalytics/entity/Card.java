package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cards")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "elixir >= 1 AND elixir <= 10")
@Check(constraints = "rarity IN ('Common', 'Rare', 'Epic', 'Legendary', 'Champion')")
@Check(constraints = "arena >= 0 AND arena <= 20")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "elixir")
    private int elixir;

    @Column(name = "rarity", length = 20)
    private String rarity;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "image_url")
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}