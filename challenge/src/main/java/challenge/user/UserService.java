package challenge.user;

import challenge.exceptions.DataQueryException;
import challenge.exceptions.ObjectNotFoundException;
import challenge.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllFollowingForUser(String handle) throws DataQueryException, ObjectNotFoundException;

    List<User> getAllFollowersForUser(String handle) throws DataQueryException, ObjectNotFoundException;

    User followUser(String handle, Integer idToFollow, String handleToFollow) throws DataQueryException, ObjectNotFoundException;

    User unfollowUser(String handle, Integer idToUnfollow, String handleToUnfollow) throws DataQueryException, ObjectNotFoundException;

    Integer getShortestPathBetweenUsers(String handle, String handleToSearch) throws DataQueryException, ObjectNotFoundException;
}
