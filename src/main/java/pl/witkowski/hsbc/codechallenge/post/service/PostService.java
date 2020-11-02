package pl.witkowski.hsbc.codechallenge.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;
import pl.witkowski.hsbc.codechallenge.post.mapper.PostMapper;
import pl.witkowski.hsbc.codechallenge.post.model.Post;
import pl.witkowski.hsbc.codechallenge.post.repository.PostRepository;
import pl.witkowski.hsbc.codechallenge.user.dto.UserDto;
import pl.witkowski.hsbc.codechallenge.user.exception.UserNotFoundException;
import pl.witkowski.hsbc.codechallenge.user.service.UserService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    private final PostMapper postMapper;

    public PostDto createPost(final Long userId, final String message) {
        UserDto userDto = userService.findUser(userId)
                .orElseGet(() -> userService.createUserWithId(userId));

        Post post = postMapper.createPost(userDto.getId(), message);
        Post savedPost = postRepository.save(post);

        return postMapper.toDto(savedPost);
    }

    public Collection<PostDto> getUserPostsPaginated(final Long userId, final Pageable pageable) {
        UserDto userDto = userService.getUser(userId);
        if (userDto == null) {
            throw new UserNotFoundException(userId);
        }

        return postRepository.findAllByUserId(userDto.getId(), pageable)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public Collection<PostDto> getUserTimelinePosts(final Long userId, final Pageable pageable) {
        UserDto userDto = userService.getUser(userId);
        Set<Long> friendsIds = userDto.getFriendsIds();

        return getTimelinePostsPaginated(friendsIds, pageable);
    }

    private Collection<PostDto> getTimelinePostsPaginated(final Set<Long> friendsIds, final Pageable pageable) {
        return postRepository.findAllByUserIdIn(friendsIds, pageable)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
