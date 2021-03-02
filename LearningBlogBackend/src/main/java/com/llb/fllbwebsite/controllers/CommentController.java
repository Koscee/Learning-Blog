package com.llb.fllbwebsite.controllers;

import com.llb.fllbwebsite.domain.Comment;
import com.llb.fllbwebsite.services.CommentService;
import com.llb.fllbwebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
public class CommentController {

    private final CommentService commentService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public CommentController(CommentService commentService, ValidationErrorService validationErrorService) {
        this.commentService = commentService;
        this.validationErrorService = validationErrorService;
    }

    // Create Comment [ @route: /api/post/postTitle/comment  @access: private]
    @PostMapping("/{postTitle}/comment")
    public ResponseEntity<?> createComment(@PathVariable String postTitle, @RequestParam String userEmail, @Valid @RequestBody Comment comment, BindingResult result){
        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Comment newComment = commentService.saveOrUpdateComment(comment, postTitle, userEmail);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    // Get all Comments  [ @route: /api/post/comments/all  @access: public]
    @GetMapping("/comments/all")
    public ResponseEntity<Iterable<Comment>> getAllComments(){
        Iterable<Comment> comments = commentService.findAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
