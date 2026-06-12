package com.vishalsingh.chatapp.Entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class UserEntity {
    @Id
    private String id;
    @NonNull
    @Indexed(unique = true, sparse = true)
    private String username;
    @NonNull
    private String password;
}
