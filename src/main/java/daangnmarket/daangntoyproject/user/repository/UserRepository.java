package daangnmarket.daangntoyproject.user.repository;


import daangnmarket.daangntoyproject.user.domain.User;
import daangnmarket.daangntoyproject.user.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    UserDto findByUserIdOrEmail(String userId, String email);

    List<User> findByUserIdIn(List<String> userIds);
}
