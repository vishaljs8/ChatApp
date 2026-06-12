package com.vishalsingh.chatapp.Repository;

import com.vishalsingh.chatapp.Entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<MessageEntity,String> {
}
