package com.aurindo.posterr.application.api.post.response;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Builder
public class PostResponse {

    private String postId;
    private PostCreator creator;
    private String content;
    private Post.PostType type;
    private OffsetDateTime created;

    public static PostResponse factory(Post post) {

        User creator = post.getCreator();
        PostCreator postCreator = PostCreator.builder().id(creator.getId()).userName(creator.getUsername()).build();

        return PostResponse.builder().
                postId(post.getId()).
                creator(postCreator).
                content(post.getContent()).
                type(post.getType()).
                created(post.getCreated()).build();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ToString
    @Getter
    @Builder
    public static class PostCreator {
        private String id;
        private String userName;
    }

}
