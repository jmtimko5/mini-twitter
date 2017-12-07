package challenge;

import challenge.exceptions.DataQueryException;
import challenge.model.UserAuth;
import challenge.user.UserController;
import challenge.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
public class UserDetailServiceImpl implements UserDetailsService{


    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserDetails loadUserByUsername(String handle) throws UsernameNotFoundException {
        challenge.model.User user = null;
        try {
            user = userRepository.getUserByHandle(handle);
        } catch (DataQueryException e) {
            logger.error("Error querying user %s in authentication process");
        }

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new UserAuth(user);
    }


}

