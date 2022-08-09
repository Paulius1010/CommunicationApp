package lt.example.communication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String senderEmail;

    private String text;

    private LocalDateTime time;

    public Message(User user, String senderEmail, String text, LocalDateTime time) {
        this.user = user;
        this.senderEmail = senderEmail;
        this.text = text;
        this.time = time;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + user +
                ", senderEmail='" + senderEmail + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }
}
