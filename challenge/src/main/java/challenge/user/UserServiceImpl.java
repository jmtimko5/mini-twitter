package challenge.user;

import challenge.exceptions.DataQueryException;
import challenge.exceptions.ObjectNotFoundException;
import challenge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllFollowingForUser(String handle) throws DataQueryException, ObjectNotFoundException {
        User tweeter = userRepository.getUserByHandle(handle);

        if(tweeter == null){
            throw new ObjectNotFoundException(String.format("User %s not found", handle));
        }

        return userRepository.getAllFollowingForUser(tweeter.getId());
    }

    @Override
    public List<User> getAllFollowersForUser(String handle) throws DataQueryException, ObjectNotFoundException {
        User tweeter = userRepository.getUserByHandle(handle);

        if(tweeter == null){
            throw new ObjectNotFoundException(String.format("User %s not found", handle));
        }

        return userRepository.getAllFollowersForUser(tweeter.getId());
    }

    @Override
    public User followUser(String handle, Integer idToFollow, String handleToFollow) throws DataQueryException, ObjectNotFoundException {
        User tweeter = userRepository.getUserByHandle(handle);

        if(tweeter == null){
            throw new ObjectNotFoundException(String.format("User %s not found", handle));
        }

        User toFollow;
        if(idToFollow == null){
            toFollow = userRepository.getUserByHandle(handleToFollow);
        } else{
            toFollow = userRepository.getUserById(idToFollow);
        }

        if(toFollow == null){
            throw new ObjectNotFoundException(String.format("User %s or id %d not found", handleToFollow, idToFollow));
        }

        return userRepository.followUser(tweeter.getId(), toFollow.getId());


    }

    @Override
    public User unfollowUser(String handle, Integer idToUnfollow, String handleToUnfollow) throws DataQueryException, ObjectNotFoundException {
        User tweeter = userRepository.getUserByHandle(handle);

        if(tweeter == null){
            throw new ObjectNotFoundException(String.format("User %s not found", handle));
        }

        User toUnfollow;
        if(idToUnfollow == null){
            toUnfollow = userRepository.getUserByHandle(handleToUnfollow);
        } else{
            toUnfollow = userRepository.getUserById(idToUnfollow);
        }

        if(toUnfollow == null){
            throw new ObjectNotFoundException(String.format("User %s or id %d not found", handleToUnfollow, idToUnfollow));
        }

        return userRepository.unfollowUser(tweeter.getId(), toUnfollow.getId());
    }

    @Override
    public Integer getShortestPathBetweenUsers(String handle, String handleToSearch) throws DataQueryException, ObjectNotFoundException {
        User forward = userRepository.getUserByHandle(handle);
        User backward = userRepository.getUserByHandle(handleToSearch);

        if(forward == null){
            throw new ObjectNotFoundException(String.format("User %s not found", handle));
        }

        if(backward == null){
            throw new ObjectNotFoundException(String.format("User %s not found", handleToSearch));
        }

        List<User> allUsers = getAllUsers();

        Map<User, Integer> forwardDistance = new HashMap<User, Integer>();
        Map<User, Integer> backwardDistance = new HashMap<User, Integer>();

        Queue<User> forwardQueue = new LinkedList<User>();
        Queue<User> backwardQueue = new LinkedList<User>();

        for(User u : allUsers){
            forwardDistance.put(u, Integer.MAX_VALUE);
            backwardDistance.put(u, Integer.MAX_VALUE);

        }
        forwardDistance.put(forward, 0);
        backwardDistance.put(backward, 0);

        forwardQueue.add(forward);
        backwardQueue.add(backward);

        while(!forwardQueue.isEmpty() || !backwardQueue.isEmpty()){
            if(!forwardQueue.isEmpty()){
                User currentForward = forwardQueue.remove();
                List<User> forwardChildren = getAllFollowingForUser(currentForward.getHandle());

                for(User u : forwardChildren){
                    Integer currentDist = forwardDistance.get(u);
                    if (currentDist == Integer.MAX_VALUE){
                        forwardDistance.put(u, forwardDistance.get(currentForward) + 1);
                        forwardQueue.add(u);
                    }

                    if(backwardDistance.get(u) != Integer.MAX_VALUE){
                        return backwardDistance.get(u) + forwardDistance.get(u);
                    }
                }
            }

            if(!backwardQueue.isEmpty()){
                User currentBackward = backwardQueue.remove();
                List<User> backwardChildren = getAllFollowersForUser(currentBackward.getHandle());

                for(User u : backwardChildren){
                    Integer currentDist = backwardDistance.get(u);
                    if (currentDist == Integer.MAX_VALUE){
                        backwardDistance.put(u, backwardDistance.get(currentBackward) + 1);
                        backwardQueue.add(u);
                    }

                    if(forwardDistance.get(u) != Integer.MAX_VALUE){
                        return backwardDistance.get(u) + forwardDistance.get(u);
                    }
                }
            }
        }
        return -1;
    }


    private List<User> getAllUsers() throws DataQueryException {
        return userRepository.getAllUsers();
    }
}
