package pl.witkowski.hsbc.codechallenge.wall.dto;

import lombok.Value;
import org.springframework.data.domain.Pageable;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;

import java.util.Collection;

@Value
public class UserWallResponse {
    Long userId;
    Collection<PostDto> posts;
    Pageable pageable;
}
