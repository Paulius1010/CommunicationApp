package lt.example.communication.services;

import lt.example.communication.models.Message;
import lt.example.communication.models.User;
import lt.example.communication.payloads.requests.MessageRequest;
import lt.example.communication.payloads.responses.MessageResponse;
import lt.example.communication.payloads.responses.UserMessageStatistic;
import lt.example.communication.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public List<Message> getAllUserMessages() {
        String currentPrincipalEmail = userService.getCurrentPrincipalEmail();
        User user = userService.getUserByEmail(currentPrincipalEmail).orElse(null);
        if(user != null){
            return messageRepository.findByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public List<UserMessageStatistic> getStatistics() {
        List<User> users = userService.getAllUsers();
        List<UserMessageStatistic> userMessageStatistics = new ArrayList<>();
        for (User user : users) {
            List<Message> messages = user.getMessages();
            Integer count = messages.size();
            Integer average = 0;
            if (count != 0) {
                Integer sum = messages.stream().map(message -> message.getText().length()).reduce(0, Integer::sum);
                average = sum/count;
                userMessageStatistics.add(new UserMessageStatistic(
                        user.getEmail(),
                        messages.size(),
                        messages.get(0).getTime(),
                        messages.get(messages.size()-1).getTime(),
                        average,
                        messages.get(messages.size()-1).getText()
                ));
            }
        }
        return userMessageStatistics;
    }

    public ResponseEntity<?> saveNewMessage(MessageRequest messageRequest) {
        String currentPrincipalEmail = userService.getCurrentPrincipalEmail();
        User recipient = userService.getUserByEmail(messageRequest.getRecipientEmail()).orElse(null);
        User sender = userService.getUserByEmail(currentPrincipalEmail).orElse(null);
        if(sender != null && recipient != null){
            Message message = new Message(recipient, sender.getEmail(), messageRequest.getText(), LocalDateTime.now());
            messageRepository.save(message);
            return ResponseEntity.ok(new MessageResponse("Message sent successfully!"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: message not sent! Email address is not valid."));
        }
    }
}
