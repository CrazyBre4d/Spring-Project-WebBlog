package com.vlas.blogsiteproject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private long userDetailId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "city")
    private String city;

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_registration")
    private LocalDate dateOfRegistration;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(mappedBy = "userDetail", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private User user;

    public UserDetail() {
    }

    public UserDetail(String name, String surname, int age, String city, String description, String email, LocalDate dateOfRegistration) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.city = city;
        this.description = description;
        this.email = email;
        this.dateOfRegistration = dateOfRegistration;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "userDetailId=" + userDetailId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", dateOfRegistration=" + dateOfRegistration +
                ", user=" + user +
                '}';
    }
}
