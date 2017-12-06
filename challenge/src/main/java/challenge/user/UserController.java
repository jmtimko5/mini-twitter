package challenge.user;
import challenge.messages.MessagesService;
import challenge.model.Message;
import challenge.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Base64;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/following", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getFollowingForUser(@RequestHeader("Authorization") String authHeader){

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        List<User> result = userService.getAllFollowingForUser(handle);

        return new ResponseEntity<List<User>>(result, HttpStatus.OK);

    }
}
