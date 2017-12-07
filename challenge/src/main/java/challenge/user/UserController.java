package challenge.user;
import challenge.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/network")
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/following", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getFollowingForUser(@RequestHeader("Authorization") String authHeader){

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        List<User> result = userService.getAllFollowingForUser(handle);

        return new ResponseEntity<List<User>>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/followers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getFollowersForUser(@RequestHeader("Authorization") String authHeader){

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        List<User> result = userService.getAllFollowersForUser(handle);

        return new ResponseEntity<List<User>>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ResponseEntity<User> followUser(@RequestHeader("Authorization") String authHeader,
                                                 @RequestParam(value = "id", required=false) Integer idToFollow,
                                                 @RequestParam(value = "handle", required=false) String handleToFollow){


        if (idToFollow == null && (handleToFollow == null || handleToFollow.isEmpty())) {
            throw new IllegalArgumentException("Please pass either a user id or handle to follow.");
        }

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        User result = userService.followUser(handle, idToFollow, handleToFollow);

        return new ResponseEntity<User>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/unfollow", method = RequestMethod.POST)
    public ResponseEntity<User> unfollowUser(@RequestHeader("Authorization") String authHeader,
                                           @RequestParam(value = "id", required=false) Integer idToUnfollow,
                                           @RequestParam(value = "handle", required=false) String handleToUnfollow) throws SQLException {


        if (idToUnfollow == null && (handleToUnfollow == null || handleToUnfollow.isEmpty())) {
            throw new IllegalArgumentException("Please pass either a user id or handle to follow.");
        }

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        User result = userService.unfollowUser(handle, idToUnfollow, handleToUnfollow);

        return new ResponseEntity<User>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "/shortestpath/{handleToSearch}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getShortestPathToUser(@RequestHeader("Authorization") String authHeader, @PathVariable String handleToSearch){

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        Integer result = userService.getShortestPathBetweenUsers(handle, handleToSearch);

        return new ResponseEntity<Integer>(result, HttpStatus.OK);

    }



    @ExceptionHandler({IllegalArgumentException.class, UsernameNotFoundException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({SQLException.class})
    void handleSQLFailures(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
