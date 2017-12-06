package challenge.messages;

import challenge.model.Message;
import challenge.model.User;
import challenge.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessagesServiceImpl implements MessagesService{

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Message> getAllMessagesForUser(String handle, String[] keywords) {

        //TODO: implement exception handling here
//        if(user == null) {
//            throw new ObjectNotFoundException()
//        }

        User tweeter = userRepository.getUserByHandle(handle);

        List<Message> userMessages =  messagesRepository.getAllMessagesForUser(tweeter.getId());

        if (keywords == null || keywords.length == 0){
            return userMessages;
        } else {
            return userMessages.stream()
                    .filter(message -> Arrays.stream(keywords)
                            .anyMatch(word -> message.containsKeyword(word)))
                            .collect(Collectors.toList());
        }
    }
}
