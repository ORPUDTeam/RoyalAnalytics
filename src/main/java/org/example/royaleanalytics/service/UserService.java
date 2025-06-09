package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.entity.UserDeck;
import org.example.royaleanalytics.mapper.UserCacheMapper;
import org.example.royaleanalytics.mapper.UserMapper;
import org.example.royaleanalytics.repository.UserCacheRepository;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDeckService userDeckService;
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
            User user = optional.get();


            return userMapper.mapToPlayerDto();
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

    private PlayerDto getCreatedPlayer(User user){
        UserCache userCache = user.getUserCache();
        UserDeck userDeck = userDeckService.get(user);
        return userMapper.mapToPlayerDto(user, userCache, userDeck)
    }
}
