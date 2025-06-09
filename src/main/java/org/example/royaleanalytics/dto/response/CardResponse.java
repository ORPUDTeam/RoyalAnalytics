package org.example.royaleanalytics.dto.response;

import lombok.Data;

@Data
public class CardResponse {
    private Integer id;
    private String name;
    private Integer elixir;
    private String rarity;
    private Integer arena;
    private String description;
    private String imageUrl;
}