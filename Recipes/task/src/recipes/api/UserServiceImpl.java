package recipes.api;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.entity.User;
import recipes.persistence.dao.UserRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("User")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Synchronized
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUserEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9+_-]{1,}@[A-Za-z0-9+_-]{1,}+.[A-Za-z0-9+_-]{1,}");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    @Override
    public boolean isValidPassword(String password) {
        return (password.trim()).length() > 7;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
        return user;
    }

    @Override
    public String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getPrincipal().toString();
    }
}
