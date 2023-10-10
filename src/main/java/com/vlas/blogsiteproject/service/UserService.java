package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.entities.Post;
import com.vlas.blogsiteproject.entities.User;

import java.util.List;

public interface UserService {
    public void saveUser(User user);

    public List<User> getAllUsers();

    public void deleteUser(Long id);

    public User getUser(Long id);
    public User findByUsername(String username);
}
