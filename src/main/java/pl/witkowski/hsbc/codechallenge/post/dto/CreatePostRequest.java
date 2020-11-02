package pl.witkowski.hsbc.codechallenge.post.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Value
public class CreatePostRequest {

    @Parameter(description = "id of user for which you would like to create post")
    @NotNull
    Long userId;

    @Parameter(description = "message of post")
    @Length.List({
            @Length(min = 1, message = "The post must be at least 5 characters"),
            @Length(max = 140, message = "The post must be less than 140 characters")
    })
    String message;
}
