package org.example.spring_security_jwt.Controllers;

import org.example.spring_security_jwt.Repository.ChatRepository;
import org.example.spring_security_jwt.Repository.UserRepository;
import org.example.spring_security_jwt.models.Chat;
import org.example.spring_security_jwt.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    // Send a chat message
    @PostMapping("/send")
    public Chat sendMessage(@RequestBody Chat chat, Principal principal) {
        String senderUsername = principal.getName();
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(chat.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Chat newChat = new Chat(sender, receiver, chat.getMessage());
        return chatRepository.save(newChat);
    }

    // Get chat history between two users
    @GetMapping("/history/{receiverId}")
    public List<Chat> getChatHistory(@PathVariable Long receiverId, Principal principal) {
        String senderUsername = principal.getName();
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        List<Chat> sentMessages = chatRepository.findBySenderAndReceiver(sender, receiver);
        List<Chat> receivedMessages = chatRepository.findByReceiverAndSender(sender, receiver);

        sentMessages.addAll(receivedMessages);
        sentMessages.sort((c1, c2) -> c1.getTimestamp().compareTo(c2.getTimestamp()));
        return sentMessages;
    }
}
