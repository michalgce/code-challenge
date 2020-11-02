package pl.witkowski.hsbc.codechallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.witkowski.hsbc.codechallenge.post.controller.PostsController;
import pl.witkowski.hsbc.codechallenge.post.dto.CreatePostRequest;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;
import pl.witkowski.hsbc.codechallenge.wall.controller.WallController;
import pl.witkowski.hsbc.codechallenge.wall.dto.UserWallResponse;

import javax.transaction.Transactional;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WallIntegrationTest {

    @Autowired
    private PostsController postsController;

    @Autowired
    private WallController wallController;

    @Test
    @Sql(scripts = "/initial/1_create_users.sql")
    @Transactional
    public void printAllMessagesOnWallForSelectedUserTest() {
        //given
        String messageTemplate = "Message From First User";
        Long userId = 1L;

        //when
        for (int i = 0; i < 5; i++) {
            CreatePostRequest createPostRequest = new CreatePostRequest(userId, messageTemplate + i);
            postsController.createPost(createPostRequest);
        }

        CreatePostRequest createPostRequest = new CreatePostRequest(2L, "Second message from second user ");
        postsController.createPost(createPostRequest);

        //then
        UserWallResponse userWall = wallController.getUserWall(userId, Pageable.unpaged());
        Collection<PostDto> posts = userWall.getPosts();

        assertThat(posts).hasSize(5);
        assertThat(posts).allMatch(postDto -> postDto.getMessage().contains(messageTemplate));
        assertThat(posts).allMatch(postDto -> postDto.getUserId().equals(userId));
    }

}
