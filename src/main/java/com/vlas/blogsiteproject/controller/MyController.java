package com.vlas.blogsiteproject.controller;

import com.vlas.blogsiteproject.config.ImageSaver;
import com.vlas.blogsiteproject.entities.Post;
import com.vlas.blogsiteproject.entities.User;
import com.vlas.blogsiteproject.entities.UserDetail;
import com.vlas.blogsiteproject.service.PostService;
import com.vlas.blogsiteproject.service.UserDetailsService;
import com.vlas.blogsiteproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MyController {
    @Autowired
    private PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //private static final String UPLOAD_DIR = "D:/post_images"; // Директория для загрузки файлов

    @RequestMapping("/home")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        model.addAttribute("loggedIn", loggedIn);
        return "index";
    }

    @RequestMapping("/post")
    public String postPage(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "post";
    }

    @RequestMapping("/archive")
    public String allBlogs(Model model, Authentication authentication) {
        List<User> usersList = userService.getAllUsers();
        model.addAttribute("usersList", usersList);
        return "archive";
    }


    @RequestMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post,
                           @RequestParam("imageFile") MultipartFile imageFile, Authentication authentication) {
        LocalDateTime localDateTime = LocalDateTime.now();

        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!imageFile.isEmpty()) {
            try {
                String imageFilePath = ImageSaver.save(imageFile);
                post.setDateTime(localDateTime);
                post.setUser(userService.findByUsername(username));
                post.setPicture(imageFilePath);
                postService.savePost(post);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        UserDetail userDetail = new UserDetail();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("userDetail", userDetail);
        return "registration";
    }

    @RequestMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, @ModelAttribute("userDetail")UserDetail userDetail,
                               @RequestParam("imageFile") MultipartFile imageFile, Authentication authentication) {
        if (!imageFile.isEmpty()) {
            try {
                String imageFilePath = ImageSaver.save(imageFile);
                LocalDate date = LocalDate.now();
                String hashPassword = passwordEncoder.encode(user.getPassword());

                user.setPassword(hashPassword);
                userDetail.setDateOfRegistration(date);
                userDetail.setProfileImage(imageFilePath);

                user.setUserDetail(userDetail);
                userDetail.setUser(user);

                userService.saveUser(user);
                userDetailsService.saveUserDetail(userDetail);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }return "redirect:/";
    }

    @RequestMapping("/my-blog")
    public String showMyPosts(Model model, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Post> postList = userService.findByUsername(username).getPostList();
        model.addAttribute("posts", postList);
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "page";
    }

}
