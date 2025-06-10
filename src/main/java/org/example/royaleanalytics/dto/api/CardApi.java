package org.example.royaleanalytics.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardApi {
    private String name;
    @JsonProperty("elixirCost")
    private Integer elixir;
    private String rarity;
    private IconUrls iconUrls;
}
