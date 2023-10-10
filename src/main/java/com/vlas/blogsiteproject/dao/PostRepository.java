package com.vlas.blogsiteproject.dao;

import com.vlas.blogsiteproject.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

/*    public void savePost(Post post);

    public List<Post> getAllPosts();

    public void deletePost(int id);*/
}
