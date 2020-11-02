package pl.witkowski.hsbc.codechallenge.timeline.dto;

import lombok.Value;
import org.springframework.data.domain.Pageable;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;

import java.util.Collection;

@Value
public class UserTimelineResponse {
    Long userId;
    Collection<PostDto> posts;
    Pageable pageable;
}
