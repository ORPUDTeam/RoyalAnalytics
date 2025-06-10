package org.example.royaleanalytics.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IconUrls {
    @JsonProperty("medium")
    private String mediumUrl;

    @JsonProperty("evolutionMedium")
    private String evolutionMediumUrl;
}