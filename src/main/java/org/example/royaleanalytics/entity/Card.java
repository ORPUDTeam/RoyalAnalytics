package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import org.example.royaleanalytics.type.DataJson;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer elixir;

    @Column(nullable = false, length = 20)
    private String rarity;

    @Column(nullable = false)
    private Integer arena;

    @JdbcTypeCode(SqlTypes.JSON)
    private DataJson data;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
