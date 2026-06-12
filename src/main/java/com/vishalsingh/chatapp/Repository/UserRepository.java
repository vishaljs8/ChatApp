package com.vishalsingh.chatapp.Repository;

import com.vishalsingh.chatapp.Entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity,String> {
    UserEntity findByUsername(String username);
}
