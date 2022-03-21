package com.aurindo.posterr.domain.service.post;

import com.aurindo.posterr.domain.model.User;

public interface PostRulesService {

    boolean rateLimit(User user, int limit);

}
