package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.entities.Post;
import com.vlas.blogsiteproject.entities.User;
import com.vlas.blogsiteproject.entities.UserDetail;

import java.util.List;

public interface UserDetailsService {
    public void saveUserDetail(UserDetail userDetail);

    public List<UserDetail> getAllUsers();

    public void deleteUserDetail(Long id);

    public UserDetail getUserDetail(Long id);
    //public User findByUsername(String username);
}
