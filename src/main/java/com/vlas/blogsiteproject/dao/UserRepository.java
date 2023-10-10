package com.vlas.blogsiteproject.dao;

import com.vlas.blogsiteproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);
}
