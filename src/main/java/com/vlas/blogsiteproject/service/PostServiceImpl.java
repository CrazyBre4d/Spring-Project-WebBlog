package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.dao.PostRepository;
import com.vlas.blogsiteproject.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post getPost(Long id) {
        Post post = null;
        Optional<Post> optional = postRepository.findById(id);
        if(optional.isPresent()){
            post = optional.get();
        }
        return post;
    }

}
