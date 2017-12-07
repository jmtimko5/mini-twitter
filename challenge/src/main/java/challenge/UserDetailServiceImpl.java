package challenge;

import challenge.model.UserAuth;
import challenge.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Configuration
public class UserDetailServiceImpl implements UserDetailsService{


    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String handle) throws UsernameNotFoundException {
        challenge.model.User user = userRepository.getUserByHandle(handle);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new UserAuth(user);
    }


}

