package com.hypeboy.HoTalking.domain.post.controller;

import com.hypeboy.HoTalking.domain.post.service.PostService;
import com.hypeboy.HoTalking.domain.post.entity.dto.request.CreatePostRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/post")
@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest request) throws Exception {
        return postService.createPost(request);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        return postService.deletePost(id);
    }

}