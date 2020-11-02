package pl.witkowski.hsbc.codechallenge.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.witkowski.hsbc.codechallenge.configuration.ApiPageable;
import pl.witkowski.hsbc.codechallenge.user.dto.*;
import pl.witkowski.hsbc.codechallenge.user.dto.request.AddFriendToUserRequest;
import pl.witkowski.hsbc.codechallenge.user.dto.request.RemoveFriendFromUserRequest;
import pl.witkowski.hsbc.codechallenge.user.dto.response.AddedFriendToUserResponse;
import pl.witkowski.hsbc.codechallenge.user.dto.response.RemovedFriendFromUserResponse;
import pl.witkowski.hsbc.codechallenge.user.dto.response.UserFollowersResponse;
import pl.witkowski.hsbc.codechallenge.user.dto.response.UserFriendsResponse;
import pl.witkowski.hsbc.codechallenge.user.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get list of liked users for user with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been created")})
    @ApiPageable
    @GetMapping("/{userId}/friends")
    public UserFriendsResponse getUserFriends(@Parameter(description = "id of user to be searched timeline") @PathVariable("userId") final Long userId,
                                              @ApiIgnore final Pageable pageable) {
        Collection<UserDto> userFriends = userService.getUserFriends(userId, pageable);

        return new UserFriendsResponse(userId, userFriends, pageable);
    }

    @Operation(summary = "Get list of followers for user with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been created")})
    @ApiPageable
    @GetMapping("/{userId}/followers")
    public UserFollowersResponse getUserFollowers(@Parameter(description = "id of user for which get all followers") @PathVariable("userId")
                                                      final Long userId,
                                                  @ApiIgnore final Pageable pageable) {
        Collection<UserDto> followers = userService.getUserFollowers(userId, pageable);

        return new UserFollowersResponse(userId, followers, pageable);
    }

    @Operation(summary = "Add friend to user with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has added to friend")})
    @PostMapping("/{userId}/friends")
    public AddedFriendToUserResponse addFriend(@Parameter(description = "id of user for which you would like to add friend") @PathVariable("userId")
                                                   final Long userId,
                                               @Valid @RequestBody final AddFriendToUserRequest addFriendToUserRequest) {
        userService.addFriendToUser(userId, addFriendToUserRequest.getFriendId());

        return new AddedFriendToUserResponse(userId, addFriendToUserRequest.getFriendId());
    }

    @Operation(summary = "Delete friend from user with specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has deleted from friend")})
    @DeleteMapping("/{userId}/friends")
    public RemovedFriendFromUserResponse removeFriend(@Parameter(description = "id of user for which you would like to remove friend") @PathVariable("userId")
                                                          final Long userId,
                                                      @Valid @RequestBody final RemoveFriendFromUserRequest removeFriendFromUserRequest) {
        userService.removeFriendFromUser(userId, removeFriendFromUserRequest.getFriendId());

        return new RemovedFriendFromUserResponse(userId, removeFriendFromUserRequest.getFriendId());
    }
}
