package pl.witkowski.hsbc.codechallenge.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.witkowski.hsbc.codechallenge.post.dto.PostDto;
import pl.witkowski.hsbc.codechallenge.post.model.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "user.id", target = "userId")
    PostDto toDto(Post post);
    Post toEntity(PostDto postDto);

    @Mapping(source = "userId", target = "user.id")
    Post createPost(Long userId, String message);
}
