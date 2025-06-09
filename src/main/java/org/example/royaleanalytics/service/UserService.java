package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.dto.response.RatingHistoryDto;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public PlayerDto getProfile(String name){
        return null; //TODO
    }

    public RatingHistoryDto getRatingHistory(Date startDate, Date endDate, String name){
        return null; //TODO
    }

    public PlayerDto getPlayer(String playerTag){
        return null; //TODO
    }

    public User getUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
