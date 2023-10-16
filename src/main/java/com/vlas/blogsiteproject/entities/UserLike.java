package com.vlas.blogsiteproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

    public UserLike() {
    }

    public UserLike(long userId, long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "UserLikes{" +
                "likeId=" + likeId +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
