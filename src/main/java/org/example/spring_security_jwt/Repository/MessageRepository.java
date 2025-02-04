package org.example.spring_security_jwt.Repository;

import org.example.spring_security_jwt.models.Message;
import org.example.spring_security_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message, Long> {

   List<Message> findByUserId(Long userId);
   List<Message> findBySenderId(Long senderId);
   List<Message> findByUser(User user);


}
