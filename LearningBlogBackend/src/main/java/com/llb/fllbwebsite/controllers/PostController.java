package com.llb.fllbwebsite.controllers;

import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.services.PostService;
import com.llb.fllbwebsite.services.ValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final ValidationErrorService validationErrorService;

    @Autowired
    public PostController(PostService postService, ValidationErrorService validationErrorService) {
        this.postService = postService;
        this.validationErrorService = validationErrorService;
    }


    // Create Post  [ @route: /api/posts  @access: private]
    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestParam String userEmail, @Valid @RequestBody Post post, BindingResult result){

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Post newPost = postService.saveOrUpdatePost(post, userEmail);

        return new ResponseEntity<Post>(newPost, HttpStatus.CREATED);
    }

    // Get all Posts  [ @route: /api/posts/all  @access: public]
    @GetMapping("/all")
    public ResponseEntity<Iterable<Post>> getAllPosts(){
        return new ResponseEntity<Iterable<Post>>(postService.findAllPosts(), HttpStatus.OK);
    }

    // Get Post by Id  [ @route: /api/posts/id/:postId  @access: public / private]
    @GetMapping("/id/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId){
        Optional<Post> post = postService.findPostById(postId);
        return new ResponseEntity<Optional<Post>>(post, HttpStatus.OK);
    }

    // Delete Post by Id  [ @route: /api/posts/id/:postId  @access: private]
    @DeleteMapping("/id/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<String>("Post with ID '" + postId + "' was deleted successfully", HttpStatus.OK);
    }


    // Get Post by title or content  [ @route: /api/posts/search?searchText=value  @access: public / private]
    @GetMapping("/search")
    public ResponseEntity<?> searchPostByTitleOrContent(@RequestParam(value = "searchText") String searchText){
        List<Post> foundPosts = postService.findPostByContentOrTitleIgnoreLetterCase(searchText);
        return new ResponseEntity<>(foundPosts, HttpStatus.OK);
    }






    // In the frontend use lodash to remove spaces from the String passed as the PathVariable
    // Get Post by title  [ @route: /api/posts/:title  @access: public / private]
//    @GetMapping("/title/{postTitle}")
//    public ResponseEntity<?> getPostByTitle(@PathVariable String postTitle){
//        postTitle = postTitle.replace("-", " ");
//        Post post = postService.findPostByTitle(postTitle);
//        return new ResponseEntity<Post>(post, HttpStatus.OK);
//    }

}
