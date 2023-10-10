package com.vlas.blogsiteproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "user_main")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "key_user_detail_id")
    private UserDetail userDetail;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "user_and_post",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    //@JoinColumn(name = "post_id")
    private List<Post> postList;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userDetail=" + userDetail +
                ", postList=" + postList +
                '}';
    }
}
