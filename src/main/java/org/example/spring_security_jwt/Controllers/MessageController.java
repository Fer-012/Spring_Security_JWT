package org.example.spring_security_jwt.Controllers;

import org.example.spring_security_jwt.models.Message;
import org.example.spring_security_jwt.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MessageController {


        @Autowired
        MessageService messageService;

        @RequestMapping("/addMessage")
        public String addMessage(Model model) {
            Message message = new Message();
            model.addAttribute("MessageForm",message);
            return "new_message";

        }

        @RequestMapping(value = "/saveMessage" , method = RequestMethod.POST)
        public String saveMessage(@ModelAttribute("MessageForm") Message message) {
            messageService.createMessage(message);
            return "redirect:/allMessages";
        }

        @RequestMapping("/allMessages")
        public String listMedecin(Model model) {
            List<Message> listMessage = messageService.getAllMessage();
            model.addAttribute("listMedecin", listMessage);
            return "liste_message";

        }
        @GetMapping("deleteMessage/{id}")
        public String deleteMessage(@PathVariable("id") long id) {

            messageService.deleteMessage(id);
            return "redirect:/allMessages";
        }


        @GetMapping("editMessage/{id}")
        public String showUpdateForm(@PathVariable("id") long id, Model model) {
            Message message = messageService.getMessageById(id);
            model.addAttribute("medecin", message);
            return "update_message";
        }

        @PostMapping("updateMessage/{id}")
        public String updateMessage(@PathVariable("id") long id, Message message) {
            messageService.updateMessage(message);
            return "redirect:/allMessages";
        }

    }
