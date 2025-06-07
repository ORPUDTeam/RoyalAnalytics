package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.entity.RatingCache;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.repository.RatingCacheRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingCacheService {

    private final RatingCacheRepository ratingCacheRepository;


    public void create(User user, int rating){
        RatingCache ratingCache = new RatingCache()
                .setRating(rating)
                .setUser(user)
                .setChangedAt(LocalDateTime.now());
        ratingCacheRepository.save(ratingCache);
    }

    public RatingCache get(User user){
        return ratingCacheRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("нет такого юзера"));
    }
}
