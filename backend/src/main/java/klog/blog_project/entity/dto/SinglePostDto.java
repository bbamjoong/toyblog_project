package klog.blog_project.entity.dto;

import java.time.LocalDateTime;
import klog.blog_project.entity.Post;
import lombok.Data;

@Data
public class SinglePostDto {
    private Long postId;
    private LocalDateTime createdDate;
    private String title;
    private String content;

    public SinglePostDto(Post post) {
        this.postId = post.getPostId();
        this.createdDate = post.getCreatedDate();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
