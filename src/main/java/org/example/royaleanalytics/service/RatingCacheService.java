package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.response.RatingHistoryDto;
import org.example.royaleanalytics.entity.RatingCache;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.mapper.RatingCacheMapper;
import org.example.royaleanalytics.repository.RatingCacheRepository;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RatingCacheService {

    private final RatingCacheRepository ratingCacheRepository;
    private final UserRepository userRepository;
    private final RatingCacheMapper ratingCacheMapper;

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

    public RatingHistoryDto getRatingHistory(Date startDate, Date endDate, String name){
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("нет такого юзера"));
        return ratingCacheMapper.mapToRatingHistoryDto(ratingCacheRepository.findByChangedAtAfterAndChangedAtBeforeAndUser(startDate.toLocalDate().atStartOfDay(), endDate.toLocalDate().atStartOfDay(), user));
    }
}
