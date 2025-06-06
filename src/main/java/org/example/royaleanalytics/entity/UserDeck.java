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
@Table(name = "user_decks")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player_tag", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status")
    private boolean status;


    @Column(
            name = "cards",
            columnDefinition = "integer[]"
    )
    private Integer[] cards;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}