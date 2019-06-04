package com.s3.SnekIO.restserver.services;

import com.mongodb.util.JSON;
import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository usersRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable("id") ObjectId id) {
        return usersRepository.findBy_id(id);
    }

    @PutMapping(value = "/{id}")
    public void updateUserById(@PathVariable("id") ObjectId id, @Valid @RequestBody User user) {
        user.set_id(id);
        usersRepository.save(user);
    }

    /**
     * Register a new user/player
     *
     * @param user Requires a String userName, String email, String password
     * @return Newly created user with _id
     */
    @PostMapping(value = "/register")
    public User register(@Valid @RequestBody User user) {

        // Set if email is already in use
        if (usersRepository.findUserByEmail(user.email) != null)
        {
            // TODO: Send an error message
            logger.warn("Email already in use: {}", user.email);
            user.set_id(ObjectId.get());
            return user;
        }

        user.set_id(ObjectId.get());

        // Hash the password using BCrypt
        user.setPassword(BCrypt.hashpw(user.password, BCrypt.gensalt(10)));

        usersRepository.save(user);
        logger.info("New user registered: {}", user);
        return user;
    }

    /**
     * Login a user/player
     *
     * @param user Requires a String email, String password
     * @return Newly created user with _id
     */
    @PostMapping(value = "/login")
    public User login(@Valid @RequestBody User user) {
        User _user = usersRepository.findUserByEmail(user.email);

        if (_user == null) {
            logger.warn("Password incorrect");
            return user;
        }

        // Check passwords
        if (BCrypt.checkpw(user.password, _user.password))
        {
            logger.info("User logged in: {}", _user);
            return _user;
        }

        logger.warn("User does not exist");
        return user;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable ObjectId id) {
        usersRepository.delete(usersRepository.findBy_id(id));
    }

    @DeleteMapping
    public void deleteUsers() {
        usersRepository.deleteAll();
    }
}
