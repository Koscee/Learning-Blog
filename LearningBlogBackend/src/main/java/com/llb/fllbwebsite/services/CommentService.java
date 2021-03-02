package com.llb.fllbwebsite.services;

import com.llb.fllbwebsite.domain.Comment;
import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.domain.User;
import com.llb.fllbwebsite.exceptions.PostTitleException;
import com.llb.fllbwebsite.exceptions.UserIdException;
import com.llb.fllbwebsite.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Comment saveOrUpdateComment(Comment comment, String postTitle, String userEmail){
        try {
            //find the post
            Post post = postService.findPostByTitle(postTitle);
            //check if user exist
            User user = userService.findUserByEmail(userEmail);
            //set relationship attributes
            comment.setPost(post);
            comment.setUser(user);
            return commentRepository.save(comment);
        } catch (PostTitleException | UserIdException e) {
            throw e;
        }

    }

    public Iterable<Comment> findAllComments(){
        return commentRepository.findAll();
    }

}
