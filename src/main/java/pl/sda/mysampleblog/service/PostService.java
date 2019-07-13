package pl.sda.mysampleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.sda.mysampleblog.model.Comment;
import pl.sda.mysampleblog.model.Post;
import pl.sda.mysampleblog.model.User;
import pl.sda.mysampleblog.repository.CommentRepository;
import pl.sda.mysampleblog.repository.PostRepository;
import pl.sda.mysampleblog.repository.RoleRepository;
import pl.sda.mysampleblog.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    UserRepository userRepository;
    PostRepository postRepository;
    RoleRepository roleRepository;
    CommentRepository commentRepository;

    @Autowired
    public PostService(UserRepository userRepository, PostRepository postRepository, RoleRepository roleRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> showAllPost() {

        return postRepository.findAll(Sort.by("id").descending());
    }

    public Post showPost(Long postId) {
        return postRepository.getOne(postId);
    }

    public List<Comment> showComments(Long postId) {

        return postRepository.getOne(postId).getComments();
    }

    public void addComment(Comment comment, Long postId, Authentication authentication) {
        if (authentication != null) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.getByEmail(userDetails.getUsername());

            comment.setUser(user);
        }
        Post post = postRepository.getOne(postId);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public void addPost(Post post, String email) {
        User user = userRepository.getByEmail(email);
        post.setUser(user);
        postRepository.save(post);
    }

    public void deletePost(Long postId) {

        postRepository.deleteById(postId);
    }

    public void updatePost(Long postId, Post updatedPost) {
        Post post = postRepository.getOne(postId);
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        post.setCategory(updatedPost.getCategory());
        postRepository.save(post);
    }

    public Post getPostById(Long postId) {
        return postRepository.getOne(postId);
    }

    public boolean isAdmin(UserDetails userDetails) {
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
        if (authorities.toString().contains("role_admin")) {
            return true;
        }
        return false;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Long getPostByCommentId(Long commentId) {
        Comment comment = commentRepository.getOne(commentId);
        return comment.getPost().getId();
    }

    public void incrementLikes(Post post) {

        post.setLikeCounter(post.getLikeCounter() + 1);
        postRepository.save(post);
    }

    public void decrementLikes(Post post) {

        post.setLikeCounter(post.getLikeCounter() - 1);
        postRepository.save(post);
    }

    public List<Post> showPostByCategory(Enum category) {


        return showAllPost().stream().filter(post -> post.getCategory().equals(category)).collect(Collectors.toList());
    }


}
