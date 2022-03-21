package com.aurindo.posterr.application.api.post.response;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@ToString
@Getter
@Builder
public class PostResponse extends RepresentationModel<PostResponse> {

    private String postId;
    private PostCreator creator;
    private String content;
    private Post.PostType type;
    private Date created;

    public static PostResponse factory(Post post) {

        User creator = post.getCreator();
        PostCreator postCreator = PostCreator.builder().id(creator.getId()).userName(creator.getUsername()).build();

        return PostResponse.builder().
                postId(post.getId().toString()).
                creator(postCreator).
                content(post.getContent()).
                type(post.getType()).
                created(post.getCreated()).build();
    }

    @ToString
    @Getter
    @Builder
    public static class PostCreator {
        private String id;
        private String userName;
    }
}
