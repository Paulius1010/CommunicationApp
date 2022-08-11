package lt.example.communication.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.example.communication.models.Message;
import lt.example.communication.payloads.requests.MessageRequest;
import lt.example.communication.payloads.responses.MessageResponse;
import lt.example.communication.payloads.responses.UserMessageStatistic;
import lt.example.communication.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/messages")
@Tag(name="Message Controller", description = "Experience service to operate messages")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/statistics")
    @Operation(summary = "get users messages statistics")
    @ApiResponse(responseCode = "200",
            description = "Statistics by users",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<UserMessageStatistic>> getStatistics() {
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.getStatistics());
    }

    @GetMapping
    @Operation(summary = "get user messages")
    @ApiResponse(responseCode = "200",
            description = "Messages of user",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<Message>> fetchAllUserMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(this.messageService.getAllUserMessages());
    }

    @PostMapping
    @Operation(summary = "save new user message sent to other user")
    @ApiResponse(responseCode = "201",
            description = "New message successfully saved in database")
    public ResponseEntity<MessageResponse> saveNewMessage(@RequestBody MessageRequest messageRequest) {
        return this.messageService.saveNewMessage(messageRequest);
    }

}
