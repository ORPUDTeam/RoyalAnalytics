package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.mapper.UserCacheMapper;
import org.example.royaleanalytics.repository.UserCacheRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCacheService {

    private final UserDeckService userDeckService;
    private final UserCacheRepository userCacheRepository;
    private final UserCacheMapper userCacheMapper;
    private final ApiService apiService;
    private final RatingCacheService ratingCacheService;


    public List<UserCache> getUserCachesToUpdate(){
        return userCacheRepository.findByUpdatedAtLessThan(LocalDateTime.now().minusDays(1))
                .orElse(null);
    }

    public UserCache updatePlayer(UserCache userCache, Player updatePlayer){
        ratingCacheService.create(userCache.getUser(), updatePlayer.getTrophies());
        return userCacheRepository.save(userCacheMapper.mapToUserCache(userCache, updatePlayer));
    }

    @Transactional
    public UserCache forceUpdate(String tag){
        UserCache userCache = userCacheRepository.findById(tag)
                .orElseThrow(() -> new RuntimeException("нет такого юзера"));
        Player player = apiService.getPlayer(tag);
        ratingCacheService.create(userCache.getUser(), player.getTrophies());
        return userCacheRepository.save(userCacheMapper.mapToUserCache(userCache, player));
    }


    public UserCache create(String tag){
        Player player = apiService.getPlayer(tag);
        UserCache userCache = userCacheRepository.save(userCacheMapper.mapToUserCache(player));
        return userCache;
    }





}
