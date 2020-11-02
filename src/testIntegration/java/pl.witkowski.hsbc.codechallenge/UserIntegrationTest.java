package pl.witkowski.hsbc.codechallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.witkowski.hsbc.codechallenge.post.controller.PostsController;
import pl.witkowski.hsbc.codechallenge.post.dto.CreatePostRequest;
import pl.witkowski.hsbc.codechallenge.post.dto.PostCreatedResponse;
import pl.witkowski.hsbc.codechallenge.user.controller.UserController;
import pl.witkowski.hsbc.codechallenge.user.dto.UserDto;
import pl.witkowski.hsbc.codechallenge.user.dto.request.AddFriendToUserRequest;
import pl.witkowski.hsbc.codechallenge.user.dto.request.RemoveFriendFromUserRequest;
import pl.witkowski.hsbc.codechallenge.user.dto.response.AddedFriendToUserResponse;
import pl.witkowski.hsbc.codechallenge.user.model.User;
import pl.witkowski.hsbc.codechallenge.user.repository.UserRepository;
import pl.witkowski.hsbc.codechallenge.user.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserIntegrationTest {

    @Autowired
    private PostsController postsController;
    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void createUserOnFirstPostOfUserTest() {
        //given
        Long userId = 1L;

        //when
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, "Test Message");
        PostCreatedResponse response = postsController.createPost(createPostRequest);

        //then
        Optional<User> user = userRepository.findById(userId);

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getId()).isEqualTo(userId);
    }

    @Test
    @Sql(scripts = "/initial/1_create_users.sql")
    @Transactional
    public void addFriendToUserTest() {
        final Long userId = 1L;
        final Long friendId = 2L;

        //given
        AddFriendToUserRequest addFriendToUserRequest = new AddFriendToUserRequest(friendId);

        //when
        AddedFriendToUserResponse response = userController.addFriend(userId, addFriendToUserRequest);

        //then
        UserDto user = userService.getUser(userId);
        assertThat(user.getFriendsIds()).contains(friendId);

        UserDto friendUser = userService.getUser(friendId);
        assertThat(friendUser.getFollowersIds()).contains(userId);
    }

    @Test
    @Sql(scripts = "/initial/1_create_users.sql")
    @Transactional
    public void removeFriendFromUserTest() {
        final Long userId = 1L;
        final Long friendId = 2L;

        //given
        RemoveFriendFromUserRequest removeFriendFromUserRequest = new RemoveFriendFromUserRequest(friendId);

        //when
        userController.removeFriend(userId, removeFriendFromUserRequest);

        //then
        UserDto user = userService.getUser(userId);
        assertThat(user.getFriendsIds()).isEmpty();

        UserDto friendUser = userService.getUser(friendId);
        assertThat(friendUser.getFollowersIds()).isEmpty();
    }
}
