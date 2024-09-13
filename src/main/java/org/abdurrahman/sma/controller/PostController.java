package org.abdurrahman.sma.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.abdurrahman.sma.entities.Post;
import org.abdurrahman.sma.response.ApiResponse;
import org.abdurrahman.sma.services.PostService;
import org.abdurrahman.sma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer userId) throws Exception {
        return new ResponseEntity<>(postService.createPost(post,userId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{postId}/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception {
        String message = postService.deletePost(postId,userId);
        ApiResponse response = new ApiResponse(message,true);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @GetMapping("/findByPost/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception {
        return new ResponseEntity<>(postService.findPostByPostId(postId),HttpStatus.ACCEPTED);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Post>> findAllPost() {
        return new ResponseEntity<List<Post>>(postService.findAllPost(),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/save/{userId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception {
        return new ResponseEntity<>(postService.savedPost(postId,userId),HttpStatus.OK);
    }

    @PutMapping("/post/like/{postId}/user/{userId}")
    public ResponseEntity<Post> postLike(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception {
        return new ResponseEntity<>(postService.likePost(postId,userId),HttpStatus.OK);
    }
}
