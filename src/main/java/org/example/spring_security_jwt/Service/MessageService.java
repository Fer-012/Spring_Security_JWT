package org.example.spring_security_jwt.Service;

import org.example.spring_security_jwt.models.Message;
import org.example.spring_security_jwt.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public Message createMessage(Message message) {
        return messageRepository.save(message) ;
    }


    public List<Message> getAllMessage() {
        return messageRepository.findAll();

    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id).get();
    }


    public Message updateMessage(Message message) {
        return messageRepository.saveAndFlush(message);

    }
    public void deleteMessage( Long id ){
        messageRepository.deleteById(id);
    }


}

