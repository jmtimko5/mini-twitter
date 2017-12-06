package challenge.messages;

import challenge.model.Message;
import challenge.model.User;
import challenge.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessagesServiceImpl implements MessagesService{

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Message> getAllMessagesForUser(String handle, String[] keywords) {

        List<Message> userMessages = getMessagesForUser(handle, keywords);
        List<Message> followingMessages = getMessagesForUserFollowing(handle, keywords);

        return Stream.concat(userMessages.stream(), followingMessages.stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getMessagesForUser(String handle, String[] keywords) {

        User tweeter = userRepository.getUserByHandle(handle);

        List<Message> userMessages =  messagesRepository.getMessagesForUser(tweeter.getId());

        return filterMessages(userMessages, keywords);
    }

    @Override
    public List<Message> getMessagesForUserFollowing(String handle, String[] keywords) {

        User tweeter = userRepository.getUserByHandle(handle);

        List<User> following = userRepository.getAllFollowingForUser(tweeter.getId());
        List<Message> followingMessages = new ArrayList<Message>();

        for(User u : following){
            followingMessages.addAll(messagesRepository.getMessagesForUser(u.getId()));
        }

        return filterMessages(followingMessages, keywords);
    }

    private List<Message> filterMessages(List<Message> messages, String[] keywords){
        if (keywords == null || keywords.length == 0){
            return messages;
        } else {
            return messages.stream()
                    .filter(message -> Arrays.stream(keywords)
                            .anyMatch(word -> message.containsKeyword(word)))
                    .collect(Collectors.toList());
        }
    }
}
