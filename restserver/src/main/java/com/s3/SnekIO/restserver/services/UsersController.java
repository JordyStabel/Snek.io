package com.s3.SnekIO.restserver.services;

import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/")
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

    @PostMapping(value = "/")
    public User createUser(@Valid @RequestBody User user) {
        user.set_id(ObjectId.get());
        usersRepository.save(user);
        return user;
    }

    @DeleteMapping(value = "/{id}")
    public void deletePet(@PathVariable ObjectId id) {
        usersRepository.delete(usersRepository.findBy_id(id));
    }

}
