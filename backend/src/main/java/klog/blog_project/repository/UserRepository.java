package klog.blog_project.repository;

import klog.blog_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(String id);

    User findByNickname(String nickname);
}
