package pl.witkowski.hsbc.codechallenge.user.dto.response;

import lombok.Value;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Value
public class UserPostsResponse {
    Long userId;
    List<String> messages;
    Pageable pageable;
}
