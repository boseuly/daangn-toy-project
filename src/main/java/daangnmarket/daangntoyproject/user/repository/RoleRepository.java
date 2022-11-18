package daangnmarket.daangntoyproject.user.repository;

import daangnmarket.daangntoyproject.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
