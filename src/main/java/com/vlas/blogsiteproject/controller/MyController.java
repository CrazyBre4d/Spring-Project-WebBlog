package com.vlas.blogsiteproject.controller;

import com.vlas.blogsiteproject.dao.UserDetailRepository;
import com.vlas.blogsiteproject.dao.UserRepository;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;


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


    private static final String UPLOAD_DIR = "D:/post_images"; // Директория для загрузки файлов


/*    @RequestMapping("/")
    public String showAllEmployees(Model model) {

        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("allEmps", allEmployees);

        return "all-employees";
    }

    @RequestMapping("/addNewEmployee")
    public String addNewEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee-info";
    }



    @RequestMapping("/updateInfo")
    public String updateEmployee(@RequestParam("empId") int id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);
        return "employee-info";
    }

    @RequestMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("empId") int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }*/


    @RequestMapping("/home")
    public String mainPage() {
        return "index";
    }

    @RequestMapping("/post")
    public String postPage(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "post";
    }

/*    @RequestMapping("/my-blog")
    public String myBlog() {
        return "page";
    }*/

    @RequestMapping("/archive")
    public String allBlogs() {
        return "archive";
    }


/*    @RequestMapping("/addPost")
    public String addPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "add-post";
    }*/


    @RequestMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post,
                           @RequestParam("imageFile") MultipartFile imageFile, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (!imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                String filePath = Paths.get(UPLOAD_DIR, fileName).toString();
                Files.write(Paths.get(filePath), imageFile.getBytes());
                post.setUser(userService.findByUsername(username));
                post.setPicture(filePath);
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
    public String registerUser(@ModelAttribute("user") User user, @ModelAttribute("userDetail") UserDetail userDetail) {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setUserDetail(userDetail);
        userDetail.setUser(user);
        userService.saveUser(user);
        userDetailsService.saveUserDetail(userDetail);
        return "redirect:/";
    }


    @RequestMapping("/my-blog")
    public String showMyPosts(Model model, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<Post> postList = userService.findByUsername(username).getPostList();
        model.addAttribute("posts", postList);
        return "page";
    }

}
