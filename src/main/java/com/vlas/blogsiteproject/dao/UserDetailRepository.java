package com.vlas.blogsiteproject.dao;

import com.vlas.blogsiteproject.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
