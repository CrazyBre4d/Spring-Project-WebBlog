package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.entities.UserLike;

public interface UserLikesService {
    public void saveLike(UserLike userLike);
    public int getAllLikesForPost(Long id);

}
