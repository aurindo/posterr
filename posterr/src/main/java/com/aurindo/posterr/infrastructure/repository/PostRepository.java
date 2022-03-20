package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, String> {

    List<Post> findAllByOrderByCreatedDesc();

    @Query("select count(*) from Post p where p.creator.id=:userId")
    Long numberPostsFromUser(@Param("userId") String userId);
}
