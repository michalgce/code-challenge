package pl.witkowski.hsbc.codechallenge.user.dto.response;

import lombok.Value;
import org.springframework.data.domain.Pageable;
import pl.witkowski.hsbc.codechallenge.user.dto.UserDto;

import java.util.Collection;

@Value
public class UserFollowersResponse {
    Long userId;
    Collection<UserDto> followers;
    Pageable pageable;
}
