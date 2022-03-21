package com.aurindo.posterr.domain.post;

import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PostRulesServiceImpl implements PostRulesService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public boolean rateLimit(User user, int limit) {
        LocalDateTime today = LocalDate.now().atTime(0,0,0,0);
        Long numberPostsToday = postRepository.numberPostsFromUserToday(user, today);
        return numberPostsToday < limit;
    }
}
