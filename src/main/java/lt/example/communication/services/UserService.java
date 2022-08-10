package lt.example.communication.services;

import lt.example.communication.models.ERole;
import lt.example.communication.models.Role;
import lt.example.communication.models.User;
import lt.example.communication.payloads.requests.SignupRequest;
import lt.example.communication.payloads.responses.MessageResponse;
import lt.example.communication.repositories.RoleRepository;
import lt.example.communication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private static final String ERROR = "Error: Role is not found.";

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public MessageResponse saveNewUser(SignupRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());

        if (signUpRequest.getPassword() != null) {
            user.setPassword(encoder.encode(signUpRequest.getPassword()));
        }

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ERROR));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(ERROR));
                    roles.add(adminRole);
                }
                else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(ERROR));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User saved successfully!");
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Set<User> getUsersByKeyword(String keyword) {
        List<User> users = userRepository.findAll();
        return users.stream().filter(user ->
                        user.getUsername().contains(keyword)
                        || user.getEmail().contains(keyword)
                        || user.getName().contains(keyword)
                        || user.getSurname().contains(keyword))
                .collect(Collectors.toSet());
    }


    public String getCurrentPrincipalEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
