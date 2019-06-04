package com.s3.SnekIO.restserver.services;

import com.s3.SnekIO.restserver.Exception.BadRequestException;
import com.s3.SnekIO.restserver.Exception.ConflictException;
import com.s3.SnekIO.restserver.Exception.NotFoundException;
import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        // Check if email is already in use
        if (usersRepository.findUserByEmail(user.email.toLowerCase()) != null)
        {
            logger.warn("Email already in use: {}", user.email);
            throw new ConflictException("This email address is already in use.");
        }

        // Hash the password using BCrypt
        user.setPassword(BCrypt.hashpw(user.password, BCrypt.gensalt(10)));

        user.set_id(ObjectId.get());
        user.setEmail(user.email.toLowerCase());
        usersRepository.save(user);
        logger.info("New user registered: {}", user);
        return user;
    }

    /**
     * Login a user/player
     *
     * @param inputUser Requires a String email, String password
     * @return Newly created user with _id
     */
    @PostMapping(value = "/login")
    public User login(@Valid @RequestBody User inputUser) {
        User outputUser = usersRepository.findUserByEmail(inputUser.email.toLowerCase());

        // Check if the user exists
        if (outputUser == null) {
            logger.warn("User does not exist");
            throw new NotFoundException("No user exists with this email address.");
        }

        // Check passwords
        if (BCrypt.checkpw(inputUser.password, outputUser.password))
        {
            logger.info("User logged in: {}", outputUser);
            return outputUser;
        } else {
            logger.warn("Wrong password");
            throw new BadRequestException("Invalid login credentials.");
        }
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
