package org.example.spring_security_jwt.Service;

import org.example.spring_security_jwt.models.Chat;
import org.example.spring_security_jwt.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
     ChatRepository chatRepository;

    public Chat createChat(Chat chat) {
        return chatRepository.save(chat) ;
    }

    public List<Chat> getAllChat() {
        return chatRepository.findAll();

    }
    public Chat getChatById(Long id) {
        return chatRepository.findById(id).get();
    }

    public Chat updateChat(Chat chat) {
        return chatRepository.saveAndFlush(chat);

    }
    public void deleteChat(Long id){
        chatRepository.deleteById(id);
    }


}
