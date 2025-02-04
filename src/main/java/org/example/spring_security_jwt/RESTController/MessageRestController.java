package org.example.spring_security_jwt.RESTController;

import org.example.spring_security_jwt.models.Message;
import org.example.spring_security_jwt.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/message")
@RestController
public class MessageRestController {

    @Autowired
    MessageService messageService;


    @PostMapping("/saveMessage")
    public Message saveMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("/allM")
    public List<Message> getallMessage() {
        return messageService.getAllMessage();
    }

    @GetMapping("/getoneMessage/{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);

    }

    @PutMapping("/updateMessage/{id}")
    public Message updateMessage(@PathVariable long id, @RequestBody Message message) {
        Message m1 =messageService.getMessageById(id);
        if(m1!=null) {
            message.setId(id);
            return messageService.updateMessage(message);

        }
        else{
            return new Message();
        }
    }

    @DeleteMapping("/Messages/{id}")
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable ("id") long id) {
        try {
            messageService.deleteMessage(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
