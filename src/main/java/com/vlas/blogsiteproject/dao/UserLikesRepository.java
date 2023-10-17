package com.vlas.blogsiteproject.dao;

import com.vlas.blogsiteproject.entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesRepository extends JpaRepository<UserLike,Long > {
    public int countUserLikesByPostId( Long id);
    public boolean existsByPostIdAndUserId( Long postId, Long userId);
    public void deleteAllByPostIdAndUserId(Long postId, Long userId);
}
