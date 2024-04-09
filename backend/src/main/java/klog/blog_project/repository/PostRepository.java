package klog.blog_project.repository;

import java.util.List;
import klog.blog_project.entity.Post;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.SinglePostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Long deletePostByPostIdAndUser(Long postId, User user);

    @Query(value = "select new klog.blog_project.entity.dto.SinglePostDto(p) "
            + "from Post p "
            + "where p.user = :user")
    List<SinglePostDto> findPostsByUser(@Param("user") User user);
}
