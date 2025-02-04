package org.example.spring_security_jwt.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    User receiver;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    LocalDateTime timestamp;

    public Chat(User sender, User receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
