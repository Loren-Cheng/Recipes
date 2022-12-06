package recipes.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
