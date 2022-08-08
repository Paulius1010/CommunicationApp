package lt.example.communication;

import lt.example.communication.models.ERole;
import lt.example.communication.models.Role;
import lt.example.communication.models.User;
import lt.example.communication.repositories.RoleRepository;
import lt.example.communication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StartRunner implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role userRole = new Role(ERole.ROLE_USER);
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        this.roleRepository.save(userRole);
        this.roleRepository.save(adminRole);

        User user = new User("paulius", "paulius@mail.com",
                this.encoder.encode("paulius"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        this.userRepository.save(user);

        User admin = new User("adminas", "adminas@mail.com",
                encoder.encode("adminas"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        admin.setRoles(adminRoles);
        this.userRepository.save(admin);
    }
}
