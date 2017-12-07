package challenge.user;

import challenge.exceptions.DataQueryException;
import challenge.exceptions.ObjectNotFoundException;
import challenge.model.User;

import java.util.List;

public interface UserRepository {

    User getUserByHandle(String handle) throws DataQueryException;

    User getUserById(int id) throws DataQueryException;

    List<User> getAllFollowingForUser(int id) throws DataQueryException;

    List<User> getAllFollowersForUser(int id) throws DataQueryException;

    User followUser(int id, int idToFollow) throws DataQueryException, ObjectNotFoundException;

    User unfollowUser(int id, int idToUnfollow) throws DataQueryException;

    List<User> getAllUsers() throws DataQueryException;
}
