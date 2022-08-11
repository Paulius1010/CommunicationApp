package lt.example.communication.services;

import lt.example.communication.models.Message;
import lt.example.communication.models.User;
import lt.example.communication.payloads.requests.MessageRequest;
import lt.example.communication.payloads.responses.UserMessageStatistic;
import lt.example.communication.repositories.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStatistics() {
        User user1 = new User("jonas", "Jonas", "Jo", "jonas@mail.com", "password");
        User user2 = new User("paulius", "Paulius", "Pa", "paulius@mail.com", "password");
        Message message1 = new Message(user1, user2.getEmail(), "Labas", LocalDateTime.now());
        Message message2 = new Message(user1, user2.getEmail(), "Viso gero", LocalDateTime.now());
        Message message3 = new Message(user2, user1.getEmail(), "Sveiki", LocalDateTime.now());
        user1.setMessages(Arrays.asList(message1, message2));
        user2.setMessages(Collections.singletonList(message3));
        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(users);

        List<UserMessageStatistic> userMessageStatistics = messageService.getStatistics();

        assertEquals(2, userMessageStatistics.size());
        assertEquals(7, userMessageStatistics.get(0).getAverageMessageLength());
        assertEquals("Viso gero", userMessageStatistics.get(0).getLastMessageText());

    }

    @Test
    void saveNewMessage() {
        MessageRequest messageRequest = new MessageRequest("jonas@mail.com", "Hello, Jonas");
        User recipient = new User("jonas", "Jonas", "Jo", "jonas@mail.com", "password");
        User sender = new User("paulius", "Paulius", "Pa", "paulius@mail.com", "password");
        when(userService.getUserByEmail("paulius@mail.com")).thenReturn(Optional.of(sender));
        when(userService.getUserByEmail("jonas@mail.com")).thenReturn(Optional.of(recipient));
        when(userService.getCurrentPrincipalEmail()).thenReturn("paulius@mail.com");
        messageService.saveNewMessage(messageRequest);

        verify(messageRepository).save(any());

    }
}