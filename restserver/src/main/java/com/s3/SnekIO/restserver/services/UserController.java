package com.s3.SnekIO.restserver.services;

import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public User register(@Valid @RequestBody User user) {
        user.set_id(ObjectId.get());
        usersRepository.save(user);
        logger.info("New user registered: {}", user.toString());
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
