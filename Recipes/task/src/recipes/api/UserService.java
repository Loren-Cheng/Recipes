package recipes.api;

import recipes.entity.User;

public interface UserService {
    User findByUserEmail(String email);

    void save(User user);

    boolean isValidEmail(String email);

    boolean isValidPassword(String password);

    String getLoggedInUser();
}
