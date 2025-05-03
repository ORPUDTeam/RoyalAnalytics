package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

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
    private int id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "elixir")
    private int elixir;

    @Column(name = "rarity", length = 20)
    private String rarity;

    @Column(name = "arena", nullable = false)
    private Integer arena;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

}