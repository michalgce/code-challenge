package pl.witkowski.hsbc.codechallenge.post.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PostDto {
    Long id;
    Long userId;
    String message;
    LocalDateTime createdAt;
}
