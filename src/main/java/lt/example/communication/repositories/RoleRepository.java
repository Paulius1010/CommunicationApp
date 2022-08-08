package lt.example.communication.repositories;

import lt.example.communication.models.ERole;
import lt.example.communication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
