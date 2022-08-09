package lt.example.communication.controllers;

import lt.example.communication.models.User;
import lt.example.communication.payloads.requests.SignupRequest;
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
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> saveNewUser(@Valid @RequestBody SignupRequest signUpRequest) {
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
