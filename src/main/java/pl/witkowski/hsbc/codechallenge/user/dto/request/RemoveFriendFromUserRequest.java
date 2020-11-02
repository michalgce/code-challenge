package pl.witkowski.hsbc.codechallenge.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFriendFromUserRequest {
    @NotNull
    private Long friendId;
}
