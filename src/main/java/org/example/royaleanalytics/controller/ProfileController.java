package org.example.royaleanalytics.controller;


import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.dto.response.RatingHistoryDto;
import org.example.royaleanalytics.service.RatingCacheService;
import org.example.royaleanalytics.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final RatingCacheService ratingCacheService;

    @GetMapping
    public PlayerDto getProfile(Authentication authentication){
        return userService.getProfile(authentication.getName());
    }

    @GetMapping("/rating-history")
    public RatingHistoryDto getRatingHistory(@RequestParam Date start_date,
                                             @RequestParam Date end_date,
                                             Authentication authentication){
        return ratingCacheService.getRatingHistory(start_date, end_date, authentication.getName());
    }


    @GetMapping("/update")
    public PlayerDto updateProfile(Authentication authentication){
        return userService.updateProfile(authentication.getName());
    }

}
