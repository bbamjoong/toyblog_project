package klog.blog_project.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    private static final long DEFAULT_LIKE = 0L;
    private static final long DEFAULT_VIEW = 1L;

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    private String content;

    @Column(name = "like_count")
    private long likeCount;

    @Column(name = "view_count")
    private long viewCount;

    // 연관관계 메서드
    public void addUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void changeInformation(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public Post(User user, String title, String content) {
        addUser(user);
        this.user = user;
        this.title = title;
        this.content = content;
        this.likeCount = DEFAULT_LIKE;
        this.viewCount = DEFAULT_VIEW;
    }
}
