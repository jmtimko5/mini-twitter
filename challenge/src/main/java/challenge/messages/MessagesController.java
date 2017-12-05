package challenge.messages;

import challenge.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1")
public class MessagesController {

//    public static final String API_BASE = "/api/v1";

//    public ResponseEntity getMessagesForUser(){

    @Autowired
    private MessagesService messagesService;


    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> getMessagesForUser(){
        String user = "";
        System.out.println("WE GOT HERE");
        List<Message> result = messagesService.getAllMessagesForUser(user);

        return new ResponseEntity<List<Message>>(result, HttpStatus.OK);

    }

}
