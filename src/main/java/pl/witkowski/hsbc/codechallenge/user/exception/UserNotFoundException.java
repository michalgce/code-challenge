package pl.witkowski.hsbc.codechallenge.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final Long userId) {
        super("User with id " + userId + " cannot be find");
    }
}
