package com.aurindo.posterr.domain.post;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.infrastructure.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post getById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(postId));
    }

    @Override
    public Long numberPostsFromUser(String userId) {
        return postRepository.numberPostsFromUser(userId);
    }

    @Override
    public List<Post> fetchPostsFromAll(Integer limit) {
        return postRepository.findAll();
    }
}
