package org.example.royaleanalytics.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardFilter {
    @Pattern(regexp = "Common|Rare|Epic|Legendary", message = "Invalid rarity value")
    @Nullable
    private String rarity;
    @Min(value = 1, message = "Elixir must be at least 1")
    @Max(value = 10, message = "Elixir must be at most 10")
    @Nullable
    private Integer elixir;
}