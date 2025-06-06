package org.example.royaleanalytics.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "generated_decks")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_tag", nullable = false)
    private User user;

    private Boolean status;

    @Column(
            name = "cards",
            columnDefinition = "integer[]"
    )
    private Integer[] cards;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}