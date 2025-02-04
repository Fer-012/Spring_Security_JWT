package org.example.spring_security_jwt.RESTController;


import org.example.spring_security_jwt.models.Chat;
import org.example.spring_security_jwt.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    ChatService chatService;


    @PostMapping("/saveChat")
    public Chat saveChat (@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    @GetMapping("/allC")
    public List<Chat> getallchat() {
        return chatService.getAllChat();
    }

    @GetMapping("/getoneChat/{id}")
    public Chat getChat(@PathVariable Long id) {
        return chatService.getChatById(id);

    }

    @PutMapping("/updateChat/{id}")
    public Chat updateChat(@PathVariable long id, @RequestBody Chat chat) {
        Chat c1 =chatService.getChatById(id);
        if(c1!=null) {
            chat.setId(id);
            return chatService.updateChat(chat);

        }
        else{
            return new Chat();
        }
    }

    @DeleteMapping("/Chats/{id}")
    public ResponseEntity<HttpStatus> deleteChat(@PathVariable ("id") long id) {
        try {
            chatService.deleteChat(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
