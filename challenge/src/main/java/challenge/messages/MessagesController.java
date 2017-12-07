package challenge.messages;

import challenge.exceptions.DataQueryException;
import challenge.exceptions.ObjectNotFoundException;
import challenge.model.Message;
import challenge.user.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessagesForUser(@RequestHeader("Authorization") String authHeader,
                                                            @RequestParam(value="search", required = false) String[] keywords) throws DataQueryException, ObjectNotFoundException {


        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        logger.info(String.format("Received request to get all messages for user: %s", handle));

        List<Message> result = messagesService.getAllMessagesForUser(handle, keywords);

        return new ResponseEntity<List<Message>>(result, HttpStatus.OK);

    }

    @ExceptionHandler({DataQueryException.class, ObjectNotFoundException.class})
    void handleSQLFailures(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
