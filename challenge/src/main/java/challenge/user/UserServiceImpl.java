package challenge.user;

import challenge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllFollowingForUser(String handle) {
        User tweeter = userRepository.getUserByHandle(handle);
        return userRepository.getAllFollowingForUser(tweeter.getId());
    }

    @Override
    public List<User> getAllFollowersForUser(String handle) {
        User tweeter = userRepository.getUserByHandle(handle);
        return userRepository.getAllFollowersForUser(tweeter.getId());
    }

    @Override
    public User followUser(String handle, Integer idToFollow, String handleToFollow) {
        User tweeter = userRepository.getUserByHandle(handle);

        User toFollow;
        if(idToFollow == null){
            toFollow = userRepository.getUserByHandle(handleToFollow);
        } else{
            toFollow = userRepository.getUserById(idToFollow);
        }

        return userRepository.followUser(tweeter.getId(), toFollow.getId());


    }

    @Override
    public User unfollowUser(String handle, Integer idToUnfollow, String handleToUnfollow) throws SQLException {
        User tweeter = userRepository.getUserByHandle(handle);

        User toUnfollow;
        if(idToUnfollow == null){
            toUnfollow = userRepository.getUserByHandle(handleToUnfollow);
        } else{
            toUnfollow = userRepository.getUserById(idToUnfollow);
        }

        return userRepository.unfollowUser(tweeter.getId(), toUnfollow.getId());
    }
}
