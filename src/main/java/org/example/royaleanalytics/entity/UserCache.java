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

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "player_tag")
    private User user;

    @Column(name = "trophies")
    private int trophies;

    @Column(name = "current_deck_id")
    private int currentDeckId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rewards",columnDefinition = "jsonb")
    private Map<String, Object> rewards;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}