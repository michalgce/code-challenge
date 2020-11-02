package pl.witkowski.hsbc.codechallenge.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.witkowski.hsbc.codechallenge.post.model.Post;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "user")
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_friends",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="friend_id")
    )
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    private Set<User> friends;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_followers",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="follower_id")
    )
    @LazyCollection(LazyCollectionOption.EXTRA)
    @EqualsAndHashCode.Exclude
    private Set<User> followers;
}
