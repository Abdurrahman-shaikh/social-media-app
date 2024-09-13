package org.abdurrahman.sma.services;

import org.abdurrahman.sma.entities.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post, Integer userId) throws Exception;
    String deletePost(Integer postID, Integer userID) throws Exception;
    List<Post> findPostByUserId(Integer postID);
    Post findPostByPostId(Integer postId) throws Exception;
    List<Post> findAllPost();
    Post savedPost(Integer postId,Integer post) throws Exception;
    Post likePost(Integer postId, Integer userID) throws Exception;

}
