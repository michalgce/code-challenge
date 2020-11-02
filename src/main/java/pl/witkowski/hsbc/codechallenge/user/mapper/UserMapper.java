package pl.witkowski.hsbc.codechallenge.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.witkowski.hsbc.codechallenge.user.dto.UserDto;
import pl.witkowski.hsbc.codechallenge.user.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserDto toDto(User user) {
        if (user == null) {
            throw new IllegalStateException("User cannot be null");
        }

        Set<Long> followersIds;
        if (user.getFollowers() == null || user.getFollowers().isEmpty()) {
            followersIds = Collections.emptySet();
        } else {
            followersIds = user.getFollowers()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        }

        Set<Long> friendsIds;
        if (user.getFriends() == null || user.getFriends().isEmpty()) {
            friendsIds = Collections.emptySet();
        } else {
            friendsIds = user.getFriends()
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toSet());
        }

        return UserDto.builder()
                .id(user.getId())
                .followersIds(followersIds)
                .friendsIds(friendsIds)
                .build();
    }

    User toEntity(UserDto userDto);

    Collection<UserDto> toDtos(Iterable<User> users);

    @Mapping(source = "userId", target = "id")
    User createUserWithId(Long userId);
}
