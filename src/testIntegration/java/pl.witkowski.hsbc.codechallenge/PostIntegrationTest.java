package pl.witkowski.hsbc.codechallenge;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import pl.witkowski.hsbc.codechallenge.post.controller.PostsController;
import pl.witkowski.hsbc.codechallenge.post.dto.CreatePostRequest;
import pl.witkowski.hsbc.codechallenge.post.model.Post;
import pl.witkowski.hsbc.codechallenge.post.repository.PostRepository;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostsController postsController;

    @Test
    public void createPostTest() {
        //given
        Long userId = 1L;
        String testMessage = "Test message";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, "Test message");

        //when
        postsController.createPost(createPostRequest);

        //then
        Page<Post> userPosts = postRepository.findAllByUserId(userId, Pageable.unpaged());

        assertThat(userPosts).hasSize(1);
        Post createdPost = userPosts.toList().get(0);
        assertThat(createdPost.getMessage()).isEqualTo(testMessage);
        assertThat(createdPost.getUser().getId()).isEqualTo(1L);
    }

    @Test
    public void createPostWithTooBigTestMessageTest() {
        //given
        Long userId = 1L;
        String testMessage = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et mag";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, testMessage);

        //when
        //then
        assertThrows(ConstraintViolationException.class, () -> postsController.createPost(createPostRequest));
    }
}
