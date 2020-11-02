package pl.witkowski.hsbc.codechallenge.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.witkowski.hsbc.codechallenge.user.dto.UserDto;
import pl.witkowski.hsbc.codechallenge.user.exception.UserNotFoundException;
import pl.witkowski.hsbc.codechallenge.user.mapper.UserMapper;
import pl.witkowski.hsbc.codechallenge.user.model.User;
import pl.witkowski.hsbc.codechallenge.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUser(final Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public Optional<UserDto> findUser(final Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto);
    }

    @Transactional
    public UserDto createUserWithId(final Long userId) {
        User user = userMapper.createUserWithId(userId);
        User savedEntity = userRepository.save(user);

        return userMapper.toDto(savedEntity);
    }

    public Collection<UserDto> getUserFriends(final Long userId, final Pageable pageable) {
        return findUser(userId)
                .map(UserDto::getFriendsIds)
                .map(userRepository::findAllById)
                .map(userMapper::toDtos)
                .orElse(Collections.emptyList());
    }

    public Collection<UserDto> getUserFollowers(final Long userId, final Pageable pageable) {
        return findUser(userId)
                .map(UserDto::getFollowersIds)
                .map(userRepository::findAllById)
                .map(userMapper::toDtos)
                .orElse(Collections.emptyList());
    }

    @Transactional
    public void addFriendToUser(final Long userId, final Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        User friendUser = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        addNewFollowerToFriendUser(user, friendUser);
        addNewFriendForUser(user, friendUser);
    }

    @Transactional
    public void removeFriendFromUser(final Long userId, final Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        User friendUser = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        removeFromFriendList(user, friendUser);
        removeFollowerFromFriendList(user, friendUser);
    }

    private void removeFromFriendList(final User user, final User friendUser) {
        user.getFriends().remove(friendUser);
        userRepository.save(user);
    }

    private void removeFollowerFromFriendList(final User follower, final User exFriend) {
        exFriend.getFollowers().remove(follower);
        userRepository.save(exFriend);
    }

    private void addNewFriendForUser(final User user, final User friendUser) {
        user.getFriends().add(friendUser);
        userRepository.save(user);
    }

    private void addNewFollowerToFriendUser(final User follower, final User friend) {
        friend.getFollowers().add(follower);
        userRepository.save(friend);
    }
}
