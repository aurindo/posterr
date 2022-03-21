package com.aurindo.posterr.domain.post;

import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.PostRepository;

public class PostRulesServiceImpl implements PostRulesService {

    private PostRepository postRepository;

    @Override
    public boolean rateLimit(User user, int limit) {
        Long numberPostsToday = postRepository.numberPostsFromUserToday(user);
        return numberPostsToday < limit;
    }
}
