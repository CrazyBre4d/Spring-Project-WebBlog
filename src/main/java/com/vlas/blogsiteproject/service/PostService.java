package com.vlas.blogsiteproject.service;


import com.vlas.blogsiteproject.entities.Post;

import java.util.List;

public interface PostService {

    public void savePost(Post post);

    public List<Post> getAllPosts();

    public void deletePost(Long id);

    public Post getPost(Long id);
}
