package pl.witkowski.hsbc.codechallenge.user.dto.response;

import lombok.Value;

@Value
public class RemovedFriendFromUserResponse {
    Long userId;
    Long friendId;
}
