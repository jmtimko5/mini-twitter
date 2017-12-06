package challenge.user;

import challenge.model.User;

public interface UserRepository {

    User getIdForUser(String username);
}
