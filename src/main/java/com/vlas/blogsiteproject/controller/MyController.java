package com.vlas.blogsiteproject.controller;

import com.vlas.blogsiteproject.config.ImageSaver;
import com.vlas.blogsiteproject.entities.Post;
import com.vlas.blogsiteproject.entities.User;
import com.vlas.blogsiteproject.entities.UserDetail;
import com.vlas.blogsiteproject.entities.UserLike;
import com.vlas.blogsiteproject.service.PostService;
import com.vlas.blogsiteproject.service.UserDetailsService;
import com.vlas.blogsiteproject.service.UserLikesService;
import com.vlas.blogsiteproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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
    @Autowired
    private UserLikesService userLikesService;

    @GetMapping("/home")
    public String mainPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        model.addAttribute("loggedIn", loggedIn);
        return "index";
    }

    @GetMapping("/post")
    public String postPage(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/archive")
    public String allBlogs(Model model, Authentication authentication) {
        List<User> usersList = userService.getAllUsers();
        model.addAttribute("usersList", usersList);
        return "archive";
    }


    @PostMapping("/savePost")
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

    @GetMapping("/registration")
    public String registration(Model model) {
        UserDetail userDetail = new UserDetail();
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("userDetail", userDetail);

        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, @Valid @ModelAttribute("userDetail")UserDetail userDetail,
                               @RequestParam("imageFile") MultipartFile imageFile, Authentication authentication,
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if (!imageFile.isEmpty()) {
            try {
                if(result.hasErrors()){
                    return "registration";
                }
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
                redirectAttributes.addFlashAttribute("message", "Регистрация успешно выполнена");

            } catch (IOException e) {
                e.printStackTrace();
            }


        }return "redirect:/";
    }

    @GetMapping("/my-blog")
    public String showMyBlog(Model model, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Post> postList = userService.findByUsername(username).getPostList();
        model.addAttribute("posts", postList);
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "page";
    }
    @GetMapping("/user-blog/{id}")
    public String showUsersPosts(Model model, @PathVariable Long id) {
        User user = userService.getUser(id);
        List<Post> postList = user.getPostList();
        model.addAttribute("posts", postList);
        model.addAttribute("user", user);
        return "page";
    }

    @PostMapping("/likePost/{postId}")
    @ResponseBody
    public String likePost(@PathVariable Long postId, Authentication authentication) {
        UserLike userLike = null;
        Post post = postService.getPost(postId);

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Long id = user.getUserId();

        userLike.setUserId(id);
        userLike.setPostId(postId);

        userLikesService.saveLike(userLike);


        return "/user-blog/{id}";
        // 16.10.23 16:46 Доделать валидацию(менюшек и на ошибки), доделать лайки, доделать фронт
        //return ResponseEntity.ok(new LikeResponse(post.getLikes()));
    }
}
