package pl.witkowski.hsbc.codechallenge.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.witkowski.hsbc.codechallenge.configuration.ApiPageable;
import pl.witkowski.hsbc.codechallenge.post.dto.CreatePostRequest;
import pl.witkowski.hsbc.codechallenge.post.dto.PostCreatedResponse;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;
import pl.witkowski.hsbc.codechallenge.post.service.PostService;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
public class PostsController {

    private final PostService postService;

    @Operation(summary = "Create Post For user with specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been created")})
    @PostMapping
    public PostCreatedResponse createPost(@Valid final CreatePostRequest createPostRequest) {
        final Long userId = createPostRequest.getUserId();
        final String message = createPostRequest.getMessage();

        PostDto post = postService.createPost(userId, message);

        return new PostCreatedResponse(post.getId());
    }

}
