package com.s3.SnekIO.restserver.handler;

import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.exception.BadRequestException;
import com.s3.SnekIO.restserver.exception.ConflictException;
import com.s3.SnekIO.restserver.exception.NotFoundException;
import com.s3.SnekIO.restserver.repositories.UserRepository;
import com.s3.SnekIO.restserver.services.UserResourceController;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserHandler {

//    private static final Logger logger = LoggerFactory.getLogger(UserHandler.class);
//
//    @Autowired
//    private UserRepository usersRepository;
//
//    public List<User> getAllUsers() {
//        return usersRepository.findAll();
//    }
//
//    public User getUserById(ObjectId id) {
//        return usersRepository.findBy_id(id);
//    }
//
//    public void updateUserById(ObjectId id, User user) {
//        user.set_id(id);
//        usersRepository.save(user);
//    }
//
//    public User login(User user) {
//        User outputUser = usersRepository.findUserByEmail(user.email.toLowerCase());
//
//        // Check if the user exists
//        if (outputUser == null) {
//            logger.warn("User does not exist");
//            throw new NotFoundException("No user exists with this email address.");
//        }
//
//        // Check passwords
//        if (BCrypt.checkpw(user.password, outputUser.password)) {
//            logger.info("User logged in: {}", outputUser);
//            return outputUser;
//        } else {
//            logger.warn("Wrong password");
//            throw new BadRequestException("Invalid login credentials.");
//        }
//    }
//
//    public User register(User user) {
//        // Check if email is already in use
//        if (usersRepository.findUserByEmail(user.email.toLowerCase()) != null) {
//            logger.warn("Email already in use: {}", user.email);
//            throw new ConflictException("This email address is already in use.");
//        }
//
//        // Hash the password using BCrypt
//        user.setPassword(BCrypt.hashpw(user.password, BCrypt.gensalt(10)));
//
//        user.set_id(ObjectId.get());
//        user.setEmail(user.email.toLowerCase());
//        usersRepository.save(user);
//        logger.info("New user registered: {}", user);
//        return user;
//    }
}
