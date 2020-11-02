package pl.witkowski.hsbc.codechallenge.wall.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.witkowski.hsbc.codechallenge.configuration.ApiPageable;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;
import pl.witkowski.hsbc.codechallenge.post.service.PostService;
import pl.witkowski.hsbc.codechallenge.wall.dto.UserWallResponse;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class WallController {

    private final PostService postService;

    @Operation(summary = "Get a wall for user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get wall"),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied",
                    content = @Content)})
    @ApiPageable
    @GetMapping("/{userId}/wall")
    public UserWallResponse getUserWall(final @PathVariable("userId") Long userId, @ApiIgnore final Pageable pageable) {
        Collection<PostDto> userPostsPaginated = postService.getUserPostsPaginated(userId, pageable);

        return new UserWallResponse(userId, userPostsPaginated, pageable);
    }
}
