package lt.example.communication.services;

import lt.example.communication.models.Message;
import lt.example.communication.models.User;
import lt.example.communication.payloads.responses.UserMessageStatistic;
import lt.example.communication.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        String currentPrincipalEmail = getCurrentPrincipalEmail();
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
            }
            userMessageStatistics.add(new UserMessageStatistic(
                    user.getEmail(),
                    messages.size(),
                    messages.get(0).getTime(),
                    messages.get(messages.size()).getTime(),
                    average,
                    messages.get(messages.size()).getText()
            ));
        }
        return userMessageStatistics;
    }

    private String getCurrentPrincipalEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
