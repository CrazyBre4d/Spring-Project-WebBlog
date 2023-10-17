package com.vlas.blogsiteproject.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "post")
public class PostDecorator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long postId;

    @Column(name = "title")
    @NotBlank(message = "Поле обязательно к заполнению")
    private String title;

    @Column(name = "body")
    @NotBlank(message = "Поле обязательно к заполнению")
    private String body;

    @Column(name = "picture")
    @NotBlank(message = "Поле обязательно к заполнению")
    private String picture;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isLiked;
    private int likes;

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> whoLiked = new HashSet<>();

    public PostDecorator() {
    }

    public PostDecorator(Post post) {
        this.title = post.getTitle();
        this.body = post.getBody();
        this.picture = post.getPicture();
        this.postId = post.getPostId();
        this.dateTime = post.getDateTime();

        this.isLiked = false;
    }

    public PostDecorator (Post post, boolean isLiked, int likes){
        this.title = post.getTitle();
        this.body = post.getBody();
        this.picture = post.getPicture();
        this.postId = post.getPostId();
        this.dateTime = post.getDateTime();
        this.isLiked = isLiked;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "PostDecorator{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", picture='" + picture + '\'' +
                ", dateTime=" + dateTime +
                ", user=" + user +
                ", isLiked=" + isLiked +
                ", whoLiked=" + whoLiked +
                '}';
    }
}