package org.example.spring_security_jwt.Repository;

import org.example.spring_security_jwt.models.Chat;
import org.example.spring_security_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderAndReceiver(User sender, User receiver);

    List<Chat> findByReceiverAndSender(User receiver, User sender);

}
