package com.vlas.blogsiteproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Логин не может быть пустым")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 6, message = "пароль должен быть не менее 6 символов")
    private String password;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "key_user_detail_id")
    private UserDetail userDetail;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
    mappedBy = "user")
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
