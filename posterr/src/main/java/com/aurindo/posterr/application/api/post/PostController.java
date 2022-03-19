package com.aurindo.posterr.application.api.post;

import com.aurindo.posterr.application.api.post.response.PostDataResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController implements PostResource {

    @Override
    public PostDataResponse fetchData(String userId) {
        return PostDataResponse.builder().userId(userId).total(10L).build();
    }
}
