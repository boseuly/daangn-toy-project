package daangnmarket.daangntoyproject.user.repository;

import daangnmarket.daangntoyproject.user.domain.EmailAuthMember;
import daangnmarket.daangntoyproject.user.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuthMember, Integer> {

    UserDto findByEmail(String email);
}
