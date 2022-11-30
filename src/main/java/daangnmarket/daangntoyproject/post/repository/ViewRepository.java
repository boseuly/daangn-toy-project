package daangnmarket.daangntoyproject.post.repository;

import daangnmarket.daangntoyproject.post.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewRepository extends JpaRepository<View, Integer> {
    Optional<View> findByPostIdAndUserId(int postId, String userId);
}
