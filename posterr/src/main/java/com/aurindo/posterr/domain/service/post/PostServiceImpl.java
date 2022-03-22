package com.aurindo.posterr.domain.service.post;

import com.aurindo.posterr.domain.exception.NotFoundException;
import com.aurindo.posterr.domain.exception.PageLimitException;
import com.aurindo.posterr.domain.exception.RateLimitException;
import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import com.aurindo.posterr.infrastructure.repository.PostRepository;
import com.aurindo.posterr.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService {

    private static final int DAILY_POST_LIMIT = 5;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRulesService postRulesService;

    @Override
    public Post getById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(postId));
    }

    @Override
    public Page<Post> fetchMyPosts(String userId, Pageable pageable, int limit) {
        checkPageLimit(limit, pageable);

        User user = User.builder().id(userId).build();
        return postRepository.findAllByCreatorOrderByCreatedDesc(user, pageable);
    }

    @Override
    public Post createPost(String userId, String content, String parentId, String type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId, User.class));

        checkRateLimit(user, DAILY_POST_LIMIT);

        Post parentPost = parentId != null ? postRepository.findById(parentId).orElse(null) : null;

        Post.PostType postType = Post.PostType.valueOf(Post.PostType.class, type);

        Post post = Post.builder().content(content).parent(parentPost).creator(user).type(postType).build();
        return postRepository.save(post);
    }

    @Override
    public Long numberPostsFromUser(String userId) {
        return postRepository.numberPostsFromUser(userId);
    }

    @Override
    public Page<Post> fetchPostsFromAll(Pageable pageable, int limit) {
        checkPageLimit(limit, pageable);

        return postRepository.findAllByOrderByCreatedDesc(pageable);
    }

    private void checkRateLimit(User user, int limit) {
        if (!postRulesService.rateLimit(user, 5)) {
            throw new RateLimitException(Post.class, user.getId(), 5);
        }
    }

    private void checkPageLimit(int limit, Pageable pageable) {
        if (pageable.getPageSize() > limit) {
            throw new PageLimitException(limit);
        }
    }
}
