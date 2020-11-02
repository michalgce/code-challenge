package pl.witkowski.hsbc.codechallenge.post.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import pl.witkowski.hsbc.codechallenge.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(length = 140)
    private String message;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}
