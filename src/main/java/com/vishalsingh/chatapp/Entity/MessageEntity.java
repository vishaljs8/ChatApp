package com.vishalsingh.chatapp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

    @Id
    private String id;
    private String content;
    private String sender;
    private String receiver;


}