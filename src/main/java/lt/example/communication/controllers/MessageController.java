package lt.example.communication.controllers;

import lt.example.communication.models.Message;
import lt.example.communication.payloads.responses.UserMessageStatistic;
import lt.example.communication.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/messages")
public class OrderController {

    private MessageService messageService;

    @Autowired
    public OrderController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserMessageStatistic>> getStatistics() {
        return ResponseEntity.ok().body(this.messageService.getStatistics());
    }

    @GetMapping
    public ResponseEntity<List<Message>> fetchAllUserMessages() {
        return ResponseEntity.ok().body(this.messageService.getAllUserMessages());
    }

    @PostMapping(value = "/{dishId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') ")
    public ResponseEntity<Message> saveNewUserOrder(@PathVariable Long dishId) {
        return ResponseEntity.ok().body(this.messageService.saveNewOrder(dishId));
    }

}
