package org.abdurrahman.sma.services.impl;

import org.abdurrahman.sma.entities.Post;
import org.abdurrahman.sma.entities.User;
import org.abdurrahman.sma.repository.PostRepository;
import org.abdurrahman.sma.repository.UserRepository;
import org.abdurrahman.sma.services.PostService;
import org.abdurrahman.sma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    UserService userService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post, Integer userId) throws Exception {

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setComments(post.getComments());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setUser(userService.findUserById(userId));

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {

        User user = userService.findUserById(userId);
        Post post = findPostByPostId(postId);

        if (post.getUser().getId() != user.getId()) {
            throw new Exception("You can not delete another users post");
        }

        postRepository.delete(post);
        return "post deleted successfully";
    }

    @Override
    public List<Post> findPostByUserId(Integer postID) {
        return postRepository.findPostByUserId(postID);
    }

    @Override
    public Post findPostByPostId(Integer postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new Exception("Post not found with the post ID" + postId);
        }
        return post.get();
    }

    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {

        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userId);

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
        } else {
            user.getSavedPost().add(post);
        }

        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userID) throws Exception {
        Post post = findPostByPostId(postId);
        User user = userService.findUserById(userID);

        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            post.getLiked().add(user);
        }

        return postRepository.save(post);
    }
}
