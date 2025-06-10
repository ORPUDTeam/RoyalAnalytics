package org.example.royaleanalytics.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CardsApi {

    @JsonProperty("items")
    private List<CardApi> cards;
}
