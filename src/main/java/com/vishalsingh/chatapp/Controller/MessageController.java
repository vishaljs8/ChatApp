package com.vishalsingh.chatapp.Controller;
import com.vishalsingh.chatapp.Entity.MessageEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {


    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public MessageEntity messaging(MessageEntity message) {
        System.out.println("Received from [" + message.getSender() + "]: " + message.getChat());
        return message;
    }
}