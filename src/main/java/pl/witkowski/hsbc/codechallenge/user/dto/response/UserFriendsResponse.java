package pl.witkowski.hsbc.codechallenge.user.dto.response;

import lombok.Value;
import org.springframework.data.domain.Pageable;
import pl.witkowski.hsbc.codechallenge.user.dto.UserDto;

import java.util.Collection;

@Value
public class UserFriendsResponse {
    Long userId;
    Collection<UserDto> friends;
    Pageable pageable;
}
