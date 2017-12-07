package challenge.messages;

import challenge.exceptions.DataQueryException;
import challenge.exceptions.ObjectNotFoundException;
import challenge.model.Message;

import java.util.List;

public interface MessagesService {

    List<Message> getAllMessagesForUser(String handle, String[] keywords) throws DataQueryException, ObjectNotFoundException;

    List<Message> getMessagesForUser(String handle, String[] keywords) throws DataQueryException, ObjectNotFoundException;

    List<Message> getMessagesForUserFollowing(String handle, String[] keywords) throws DataQueryException, ObjectNotFoundException;

}
