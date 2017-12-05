package challenge.messages;

import challenge.model.Message;

import java.util.List;

public interface MessagesRepository {

    List<Message> getAllMessagesForUser(int id);
}
