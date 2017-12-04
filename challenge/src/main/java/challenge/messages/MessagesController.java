package challenge.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/v1")
public class MessagesController {

//    public static final String API_BASE = "/api/v1";

//    public ResponseEntity getMessagesForUser(){

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public void getMessagesForUser(){
        System.out.println("WE GOT HERE");

    }

}
