package lt.example.communication.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lt.example.communication.models.User;
import lt.example.communication.payloads.requests.SignupRequest;
import lt.example.communication.payloads.responses.MessageResponse;
import lt.example.communication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    @Operation(summary = "fetch person data from database using email")
    @ApiResponse(responseCode = "200",
            description = "user object",
            content = @Content(schema = @Schema(implementation = Optional.class)))
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete user data from database using id")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PostMapping
    @Operation(summary = "Create new user in database")
    @ApiResponse(responseCode = "201",
            description = "New user successfully created in database")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> saveNewUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(this.userService.saveNewUser(signUpRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<Set<User>> getUsersByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok().body(this.userService.getUsersByKeyword(keyword));
    }

}
