package pl.sda.mysampleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.mysampleblog.model.Category;
import pl.sda.mysampleblog.model.Comment;
import pl.sda.mysampleblog.model.Post;
import pl.sda.mysampleblog.service.PostService;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PostController {
    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        List<Post> posts = postService.showAllPost();
        model.addAttribute("posts", posts);
        Set<Category> categories = new HashSet<>();
        for (Post post : posts) {
            categories.add(post.getCategory());
        }
        model.addAttribute("categories", categories);
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }
        return "posts";
    }

    @GetMapping("/addpost")
    public String addPost(Model model, Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        System.out.println("Login: " + principal.getUsername());
        System.out.println("password: " + principal.getPassword());
        model.addAttribute("post", new Post());
        List<Category> categories =
                new ArrayList<>(Arrays.asList(Category.values()));
        System.out.println(categories);
        model.addAttribute("categories", categories);
        return "addpost";
    }

    @GetMapping("/post/{postId}")
    public String showPost(@PathVariable Long postId, Model model, Authentication authentication) {
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }
        Post post = postService.showPost(postId);
        post.setComments(post.getComments()
                .stream()
                .sorted(Comparator.comparing(Comment::getId).reversed())
                .collect(Collectors.toList()));
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "post";
    }

    @GetMapping("/showpostsbycategory/{category}")
    public String showPostsByCategory(@PathVariable Category category, Model model, Authentication authentication) {
        List<Post> postsByCategory = postService.showPostByCategory(category);
        model.addAttribute("posts", postsByCategory);
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postService.isAdmin(userDetails));
        }
        return "postsbycategory";

    }

    @PutMapping("/likepost/{postId}")
    public String LikePost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        postService.incrementLikes(post);
        return "redirect:/post/" + postId;
    }

    @PutMapping("/dislikepost/{postId}")
    public String DislikePost(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        postService.decrementLikes(post);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/addcomment/{postId}/{userId}")
    public String addComment(@ModelAttribute Comment comment, @PathVariable Long postId, @PathVariable Long userId, Authentication authentication) {
        postService.addComment(comment, postId, authentication);
        return "redirect:/post/" + postId;
    }

    @DeleteMapping("/deletecomment/{commentId}")
    public String deleteComment(@PathVariable Long commentId) {
        Long postId = postService.getPostByCommentId(commentId);
        postService.deleteComment(commentId);
        return "redirect:/post/" + postId;
    }

    @PostMapping("/addnewpost")
    public String addPost(@ModelAttribute Post post, Authentication authentication) {
        UserDetails loggedUserDetails = (UserDetails) authentication.getPrincipal();

        postService.addPost(post, loggedUserDetails.getUsername());
        return "redirect:/";
    }

    @DeleteMapping("/deletepost/{postId}")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }

    @GetMapping("/update/{postId}")
    public String updatePostGet(@PathVariable Long postId, Model model) {
        Post post = postService.getPostById(postId);
        List<Category> categories =
                new ArrayList<>(Arrays.asList(Category.values()));
        System.out.println(categories);
        model.addAttribute("categories", categories);
        model.addAttribute("post", post);
        return "updatepost";
    }

    @PutMapping("/update/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute Post updatedPost) {
        postService.updatePost(postId, updatedPost);
        return "redirect:/";
    }


}



