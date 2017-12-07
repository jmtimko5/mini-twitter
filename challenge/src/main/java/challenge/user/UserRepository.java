package challenge.user;

import challenge.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {

    User getUserByHandle(String handle);

    User getUserById(int id);

    List<User> getAllFollowingForUser(int id);

    List<User> getAllFollowersForUser(int id);

    User followUser(int id, int idToFollow);

    User unfollowUser(int id, int idToUnfollow) throws SQLException;

    List<User> getAllUsers();
}
