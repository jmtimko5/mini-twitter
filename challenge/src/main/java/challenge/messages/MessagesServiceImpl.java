package challenge.messages;

import challenge.model.Message;
import challenge.model.User;
import challenge.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesServiceImpl implements MessagesService{

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Message> getAllMessagesForUser(String handle) {

        //TODO: implement exception handling here
//        if(user == null) {
//            throw new ObjectNotFoundException()
//        }


        User tweeter = userRepository.getIdForUser(handle);

        System.out.println(tweeter.getHandle());
        System.out.println(tweeter.getId());

        return messagesRepository.getAllMessagesForUser(tweeter.getId());
    }
}
