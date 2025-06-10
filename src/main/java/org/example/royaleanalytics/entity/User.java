package org.example.royaleanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false)
    public String playerTag;

    @Column(unique = true, nullable = false)
    public String username;

    @Column(nullable = false)
    public String password_hash;

    @Column(insertable = false)
    public LocalDateTime registered_at;

    @Column(insertable = false)
    public String role;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "playerTag")
    private UserCache userCache;


}
