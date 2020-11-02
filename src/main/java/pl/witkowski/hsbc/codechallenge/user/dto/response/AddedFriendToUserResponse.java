package pl.witkowski.hsbc.codechallenge.user.dto.response;

import lombok.Value;

@Value
public class AddedFriendToUserResponse {
    Long userId;
    Long friendId;
}
