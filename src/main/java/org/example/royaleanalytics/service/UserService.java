package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.PlayerDto;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.entity.UserDeck;
import org.example.royaleanalytics.mapper.UserCacheMapper;
import org.example.royaleanalytics.mapper.UserMapper;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCacheService userCacheService;
    private final UserMapper userMapper;
    private final UserCacheMapper userCacheMapper;


    public PlayerDto getProfile(String name) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return getCreatedPlayer(user);
    }

    public PlayerDto getPlayer(String playerTag) {
        return userRepository.findById(playerTag)
                .map(this::getCreatedPlayer)
                .orElseGet(() -> {
                    UserCache userCache = userCacheService.getOrCreate(playerTag);
                    return userCacheMapper.mapToPlayerDto(userCache);
                });
    }
    private PlayerDto getCreatedPlayer(User user) {
        UserCache userCache = user.getUserCache();
        return userMapper.mapToPlayerDto(user, userCache, userCache.getUserDeck());
    }
    public PlayerDto updateProfile(String name){
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("нет такого пользователя"));
        UserCache userCache = userCacheService.forceUpdate(user.playerTag);
        user.setUserCache(userCache);
        return userMapper.mapToPlayerDto(userRepository.save(user), userCache, userCache.getUserDeck());
    }

    public User getUser(Authentication authentication){
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
