package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.dao.PostRepository;
import com.vlas.blogsiteproject.dao.UserLikesRepository;
import com.vlas.blogsiteproject.dao.UserRepository;
import com.vlas.blogsiteproject.entities.User;
import com.vlas.blogsiteproject.entities.UserLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserLikesServiceImpl implements UserLikesService{
    @Autowired
    UserLikesRepository userLikesRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Transactional
    @Override
    public void saveLike(UserLike userLike) {
        userLikesRepository.save(userLike);
    }

    @Override
    public int getAllLikesForPost(Long id) {
        return userLikesRepository.countUserLikesByPostId(id);
    }

    @Override
    public boolean isLiked(Long postId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Long userId = user.getUserId();

        return userLikesRepository.existsByPostIdAndUserId(postId, userId);

    }
    @Transactional
    @Override
    public void deleteLike(Long postId, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        Long userId = user.getUserId();

        userLikesRepository.deleteAllByPostIdAndUserId(postId, userId);
    }

}
