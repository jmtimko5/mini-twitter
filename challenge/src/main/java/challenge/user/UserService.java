package challenge.user;

import challenge.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllFollowingForUser(String handle);

    List<User> getAllFollowersForUser(String handle);

    User followUser(String handle, Integer idToFollow, String handleToFollow);

    User unfollowUser(String handle, Integer idToUnfollow, String handleToUnfollow) throws SQLException;
}
