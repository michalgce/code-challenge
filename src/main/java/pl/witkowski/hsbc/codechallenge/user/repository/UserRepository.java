package pl.witkowski.hsbc.codechallenge.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.witkowski.hsbc.codechallenge.user.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
