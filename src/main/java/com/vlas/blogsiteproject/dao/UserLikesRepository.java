package com.vlas.blogsiteproject.dao;

import com.vlas.blogsiteproject.entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikesRepository extends JpaRepository<UserLike,Long > {
    public int countAllByUserId(Long id);
}
