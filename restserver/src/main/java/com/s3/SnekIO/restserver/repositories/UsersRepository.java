package com.s3.SnekIO.restserver.repositories;

import com.s3.SnekIO.restserver.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String>{
    User findBy_id(ObjectId _id);
}
