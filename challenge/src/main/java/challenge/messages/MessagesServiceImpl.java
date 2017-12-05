package challenge.messages;

import challenge.model.Message;
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
    public List<Message> getAllMessagesForUser(String user) {

        //TODO: implement exception handling here
//        if(user == null) {
//            throw new ObjectNotFoundException()
//        }


        int id = userRepository.getIdForUser(user);

        return messagesRepository.getAllMessagesForUser(id);
    }
}
