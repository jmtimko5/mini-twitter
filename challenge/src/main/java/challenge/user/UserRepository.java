package challenge.user;

import challenge.model.User;

import java.util.List;

public interface UserRepository {

    User getUserByHandle(String handle);

    User getUserById(int id);

    List<User> getAllFollowingForUser(int id);
}
