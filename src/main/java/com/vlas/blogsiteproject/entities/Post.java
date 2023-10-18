package com.vlas.blogsiteproject.entities;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
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

    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> whoLiked = new HashSet<>();

    public Post() {
    }

    public Post(String title, String body, String picture) {
        this.title = title;
        this.body = body;
        this.picture = picture;
    }

}
