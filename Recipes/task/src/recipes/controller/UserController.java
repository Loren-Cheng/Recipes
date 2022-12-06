package recipes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import recipes.api.UserService;
import recipes.entity.User;

import java.util.Optional;


@Controller
@Validated
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    @ResponseBody
    @Synchronized
    public ResponseEntity<Object> register(@RequestBody User user) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(user));
//        System.out.println(user.getUsername());
//        System.out.println("isValidEmail? :" + userService.isValidEmail(user.getUsername()));
//        System.out.println("passwordLength() :" + ((user.getPassword().trim()).length()));
        Optional<User> tmpUser = Optional.ofNullable(userService.findByUserEmail(user.getUsername()));
        if (!userService.isValidEmail(user.getUsername())) {
            return new ResponseEntity<>("Emails not valid", HttpStatus.BAD_REQUEST);
        } else if (!userService.isValidPassword(user.getPassword())) {
            return new ResponseEntity<>("Passwords not valid", HttpStatus.BAD_REQUEST);
        } else {
            if (tmpUser.isEmpty()) {
                String password = user.getPassword();
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode(password));
                userService.save(user);
                return new ResponseEntity<>("User registered.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
            }
        }
    }
}

