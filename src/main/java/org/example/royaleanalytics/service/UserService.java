package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.dto.response.RatingHistoryDto;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.mapper.UserCacheMapper;
import org.example.royaleanalytics.mapper.UserMapper;
import org.example.royaleanalytics.repository.UserCacheRepository;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheService userCacheService;
    private final UserMapper userMapper;
    private final UserCacheMapper userCacheMapper;


    public PlayerDto getProfile(String name){
        return userMapper.mapToPlayerDto(userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("нет такого пользователя")));

    }

    public PlayerDto getPlayer(String playerTag){
        Optional<User> optional = userRepository.findById(playerTag);
        if (optional.isPresent()){
            return userMapper.mapToPlayerDto(optional.get());
        } else {
            UserCache userCache = null;
            try {
                userCache = userCacheService.forceUpdate(playerTag);
            } catch (RuntimeException ex){
                userCache = userCacheService.create(playerTag);
            }
            return userCacheMapper.mapToPlayerDto(userCache);
        }
    }

    public PlayerDto updateProfile(String name){
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("нет такого пользователя"));
        user.setUserCache(userCacheService.forceUpdate(user.playerTag));
        return userMapper.mapToPlayerDto(userRepository.save(user));
    }

    public User getUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
