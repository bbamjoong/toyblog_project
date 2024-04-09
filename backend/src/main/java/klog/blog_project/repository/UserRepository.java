package klog.blog_project.repository;

import java.util.Optional;
import klog.blog_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByUserId(Long userId);
}
