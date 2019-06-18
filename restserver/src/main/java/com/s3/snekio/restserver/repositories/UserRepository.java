package com.s3.snekio.restserver.repositories;

import com.s3.snekio.restserver.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findBy_id(ObjectId _id);
    User findUserByEmail (String email);
}
