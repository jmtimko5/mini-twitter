package challenge.messages;

import challenge.exceptions.DataQueryException;
import challenge.model.Message;

import java.util.List;

public interface MessagesRepository {

    List<Message> getMessagesForUser(int id) throws DataQueryException;
}
