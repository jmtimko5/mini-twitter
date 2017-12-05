package challenge.messages;

import challenge.model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessagesRepositoryImpl implements MessagesRepository{


    @Override
    public List<Message> getAllMessagesForUser(int id) {
        return null;
    }
}
