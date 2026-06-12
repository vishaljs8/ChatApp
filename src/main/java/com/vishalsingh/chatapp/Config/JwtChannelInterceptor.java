package com.vishalsingh.chatapp.Config;

import com.vishalsingh.chatapp.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                // 1. validate token
                String username = jwtUtil.extractUsername(token);

                // 2. attach user to WebSocket session
                accessor.setUser(
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>())
                );
                System.out.println("WebSocket User: " + username);
            }
        }

        return message;
    }
}
