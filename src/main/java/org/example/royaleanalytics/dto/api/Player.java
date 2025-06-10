package org.example.royaleanalytics.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Player {
    @Getter
    @JsonProperty("tag")
    private String player_tag;
    private Integer trophies;
    @JsonProperty("currentDeck")
    private Set<CardApi> deck;
    private Map<String, Object> rewards;

    public void getPlayer_tag(String tag) {
        if (tag != null) {
            this.player_tag = tag.substring(1);
        }
    }

}
