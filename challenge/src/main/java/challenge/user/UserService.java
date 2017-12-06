package challenge.user;

import challenge.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllFollowingForUser(String handle);
}
