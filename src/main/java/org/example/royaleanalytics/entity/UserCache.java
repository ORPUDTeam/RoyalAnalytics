package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "user_cache")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserCache {

    @Id
    @Column(name = "player_tag", nullable = false, insertable = false)
    private String player_tag;

    @Column(name = "trophies")
    private Integer trophies;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rewards",columnDefinition = "jsonb")
    private Map<String, Object> rewards;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "player_tag", referencedColumnName = "player_tag", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_deck_id")
    private UserDeck userDeck;
}