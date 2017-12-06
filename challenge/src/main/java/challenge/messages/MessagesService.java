package challenge.messages;

import challenge.model.Message;

import java.util.List;

public interface MessagesService {

    List<Message> getAllMessagesForUser(String user, String[] keywords);

}
