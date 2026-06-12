package com.vishalsingh.chatapp.Controller;
import com.vishalsingh.chatapp.Entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class MessageController {
  @Autowired
  private SimpMessagingTemplate  template;

    @MessageMapping("/hello")
    public void messaging(@Payload MessageEntity message, Principal principal) {
        String sender = principal.getName();
        message.setSender(sender);
        System.out.println("Sender: " + sender);
        System.out.println("Receiver: " + message.getReceiver());
        System.out.println("Content: " + message.getContent());
        System.out.println("Principal: " + principal.getName());
        System.out.println("-------------------");
        template.convertAndSendToUser(
                message.getReceiver(),
                "/queue/messages",
                message
        );
    }
}