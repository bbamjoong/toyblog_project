package klog.blog_project.repository;

import klog.blog_project.entity.Post;
import klog.blog_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Long deletePostByPostIdAndUser(Long postId, User user);
}
