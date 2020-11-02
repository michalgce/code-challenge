package pl.witkowski.hsbc.codechallenge.user.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class UserDto {
    Long id;
    Set<Long> followersIds;
    Set<Long> friendsIds;

    public UserDto(final Long id, final Set<Long> followersIds, final Set<Long> friendsIds) {
        this.id = id;
        this.followersIds = followersIds;
        this.friendsIds = friendsIds;
    }
}
