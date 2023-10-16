package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.dao.UserLikesRepository;
import com.vlas.blogsiteproject.entities.UserLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLikesServiceImpl implements UserLikesService{
    @Autowired
    UserLikesRepository userLikesRepository;
    @Override
    public void saveLike(UserLike userLike) {
        userLikesRepository.save(userLike);
    }

    @Override
    public int getAllLikesForPost(Long id) {
        return userLikesRepository.countAllByUserId(id);
    }

}
