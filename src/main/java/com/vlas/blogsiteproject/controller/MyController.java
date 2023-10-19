package com.vlas.blogsiteproject.controller;

import com.vlas.blogsiteproject.config.ImageSaver;
import com.vlas.blogsiteproject.entities.*;
import com.vlas.blogsiteproject.service.PostService;
import com.vlas.blogsiteproject.service.UserDetailsService;
import com.vlas.blogsiteproject.service.UserLikesService;
import com.vlas.blogsiteproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        User myUser = userService.findByUsername(authentication.getName());
        model.addAttribute("myUser", myUser);
        return "index";
    }

    @GetMapping("/post")
    public String postPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Post post = new Post();
        User myUser = userService.findByUsername(authentication.getName());
        model.addAttribute("myUser", myUser);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/archive")
    public String allBlogs(Model model, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        List<User> usersList = userService.getAllUsers();
        model.addAttribute("usersList", usersList);
        User myUser = userService.findByUsername(authentication.getName());
        model.addAttribute("myUser", myUser);
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
        model.addAttribute("user",new User());
        model.addAttribute("userDetail", new UserDetail());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, @Valid @ModelAttribute("userDetail")UserDetail userDetail,
                               @RequestParam("imageFile") MultipartFile imageFile,
                               BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "registration";
        } else if (!imageFile.isEmpty()) {
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
                redirectAttributes.addFlashAttribute("message", "Регистрация успешно выполнена");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }return "redirect:/post";
    }

    @GetMapping("/my-blog")
    public String showMyBlog(Model model, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        List<Post> postList = user.getPostList();
        List<PostDecorator> postDecorators = new ArrayList<>();

        for(Post post : postList){
            boolean isLiked = userLikesService.isLiked(post.getPostId(),authentication);
            int likes = userLikesService.getAllLikesForPost(post.getPostId());
            postDecorators.add(new PostDecorator(post,isLiked, likes));
        }

        model.addAttribute("posts", postDecorators);
        return "my-page";
    }
    @GetMapping("/user-blog/{id}")
    public String showUsersPosts(Model model, @PathVariable Long id,Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(id);
        User myUser = userService.findByUsername(authentication.getName());
        List<Post> postList = user.getPostList();
        List<PostDecorator> postDecorators = new ArrayList<>();

        for(Post post : postList){
            boolean isLiked = userLikesService.isLiked(post.getPostId(),authentication);
            int likes = userLikesService.getAllLikesForPost(post.getPostId());
         postDecorators.add(new PostDecorator(post,isLiked, likes));
        }

        model.addAttribute("posts", postDecorators);
        model.addAttribute("user", user);
        model.addAttribute("myUser", myUser);
        return "page";
    }

    @GetMapping(value = "/likePost/{postId}/like")
    public String likePost(@PathVariable Long postId,
                           Authentication authentication,
                           @RequestHeader(required = false) String referer) {
        User user = userService.findByUsername(authentication.getName());
        Long userId = user.getUserId();

        if (userLikesService.isLiked(postId, authentication)) {
            userLikesService.deleteLike(postId, authentication);
        } else {
            userLikesService.saveLike(new UserLike(userId, postId));
        }

        return "redirect:" + referer;
    }
    @PostMapping(value = "/my-blog/{postId}")
    public String deletePost(@PathVariable Long postId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        if(userService.findByUsername(authentication.getName()).getUsername() == postService.getPost(postId).getUser().getUsername()){
            postService.deletePost(postId);
        } else {
            throw new NoSuchElementException();
        }
        return "redirect:/my-blog";
    }
}
