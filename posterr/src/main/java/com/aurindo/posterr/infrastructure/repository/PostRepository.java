package com.aurindo.posterr.infrastructure.repository;

import com.aurindo.posterr.domain.model.Post;
import com.aurindo.posterr.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public interface PostRepository extends PagingAndSortingRepository<Post, String> {

    Page<Post> findAllByOrderByCreatedDesc(Pageable pageable);

    @Query("select count(*) from Post p where p.creator.id=:userId")
    Long numberPostsFromUser(@Param("userId") String userId);

    Page<Post> findAllByCreatorOrderByCreatedDesc(User user, Pageable pageable);

    @Query("select count(*) from Post p where p.creator= :user and p.created >= :today")
    Long numberPostsFromUserToday(User user, LocalDateTime today);
}
