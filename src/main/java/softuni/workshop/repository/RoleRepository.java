package softuni.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.domain.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByAndAuthority(String authority);
}
