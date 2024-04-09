package klog.blog_project.repository;

import java.util.List;
import java.util.Optional;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.SingleProfileDto;
import klog.blog_project.entity.dto.SingleSimpleProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByUserId(Long userId);

    @Query(value = "select new klog.blog_project.entity.dto.SingleProfileDto(u) "
            + "from User u "
            + "where u.nickname = :nickname")
    Optional<SingleProfileDto> findUserDtoByNickname(@Param("nickname") String nickname);

    @Query(value = "select new klog.blog_project.entity.dto.SingleSimpleProfileDto(u) "
            + "from User u")
    List<SingleSimpleProfileDto> findAllSimpleProfiles();
}
