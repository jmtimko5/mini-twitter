package challenge.messages;

import challenge.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Base64;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1")
public class MessagesController {

//    public static final String API_BASE = "/api/v1";

//    public ResponseEntity getMessagesForUser(){

    @Autowired
    private MessagesService messagesService;


    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessagesForUser(@RequestHeader("Authorization") String authHeader){

        String encodedCredentials = authHeader.split(" ")[1];
        String handle = new String(Base64.getDecoder().decode(encodedCredentials)).split(":")[0];

        List<Message> result = messagesService.getAllMessagesForUser(handle);

        return new ResponseEntity<List<Message>>(result, HttpStatus.OK);

    }

}
