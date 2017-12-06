package challenge.messages;

import challenge.model.Message;

import java.util.List;

public interface MessagesService {

    List<Message> getAllMessagesForUser(String handle, String[] keywords);

    List<Message> getMessagesForUser(String handle, String[] keywords);

    List<Message> getMessagesForUserFollowing(String handle, String[] keywords);

}
