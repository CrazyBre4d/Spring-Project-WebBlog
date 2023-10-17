package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.entities.User;
import com.vlas.blogsiteproject.entities.UserLike;
import org.springframework.security.core.Authentication;

public interface UserLikesService {
    public void saveLike(UserLike userLike);
    public int getAllLikesForPost(Long id);
    public boolean isLiked(Long postId, Authentication authentication);
    public void deleteLike(Long postId, Authentication authentication);


}
