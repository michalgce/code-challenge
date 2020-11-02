package pl.witkowski.hsbc.codechallenge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.witkowski.hsbc.codechallenge.post.controller.PostsController;
import pl.witkowski.hsbc.codechallenge.post.dto.CreatePostRequest;
import pl.witkowski.hsbc.codechallenge.timeline.controller.TimelineController;
import pl.witkowski.hsbc.codechallenge.timeline.dto.UserTimelineResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeLineIntegrationTest {

    @Autowired
    private PostsController postsController;

    @Autowired
    private TimelineController timelineController;

    @Test
    @Sql(scripts = {"/initial/1_create_users.sql", "/initial/2_add_friends_to_user.sql"})
    @Transactional
    public void getTimelineForUser() {
        //given
        List<CreatePostRequest> lists = new ArrayList<>();
        for (long i = 1L; i < 4L; i++) {
            lists.add(new CreatePostRequest(i, "Test Message Second User"));
        }

        for (CreatePostRequest request : lists) {
            postsController.createPost(request);
        }

        //when
        UserTimelineResponse userTimeline = timelineController.getUserTimeline(1L, Pageable.unpaged());

        //then
        assertThat(userTimeline.getPosts()).hasSize(2);
        assertThat(userTimeline.getPosts()).allMatch(postDto -> (postDto.getUserId().equals(2L) || postDto.getUserId().equals(3L)));
        assertThat(userTimeline.getPosts()).allMatch(postDto -> !postDto.getUserId().equals(1L));
    }
}
