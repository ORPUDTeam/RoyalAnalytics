package org.example.royaleanalytics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.api.Player;
import org.example.royaleanalytics.entity.UserCache;
import org.example.royaleanalytics.entity.UserDeck;
import org.example.royaleanalytics.mapper.UserCacheMapper;
import org.example.royaleanalytics.repository.UserCacheRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerUpdateService {
    private final UserCacheRepository userCacheRepository;
    private final UserCacheMapper userCacheMapper;
    private final RatingCacheService ratingCacheService;
    private final UserDeckService userDeckService;
    private final ApiService apiService;

    public UserCache updatePlayer(UserCache userCache, Player updatePlayer) {
        ratingCacheService.create(userCache.getUser(), updatePlayer.getTrophies());
        UserDeck userDeck = userCache.getUserDeck();
        userDeckService.updateMain(updatePlayer.getDeck(), userDeck);
        return userCacheRepository.save(userCacheMapper.mapToUserCache(userCache, updatePlayer));
    }

    @Transactional
    public UserCache forceUpdate(String tag) {
        UserCache userCache = userCacheRepository.findById(tag)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Player player = apiService.getPlayer(tag);
        ratingCacheService.create(userCache.getUser(), player.getTrophies());
        userDeckService.updateMain(player.getDeck(), userCache.getUserDeck());
        return userCacheRepository.save(userCacheMapper.mapToUserCache(userCache, player));
    }

    public UserCache create(String tag) {
        Player player = apiService.getPlayer(tag);
        UserCache userCache = userCacheMapper.mapToUserCache(player);
        userCache.setUserDeck(userDeckService.createMain(player, userCache.getUser()));
        String normTag = tag.substring(1);
        userCache.setPlayer_tag(normTag);
        return userCacheRepository.save(userCache);
    }
}
