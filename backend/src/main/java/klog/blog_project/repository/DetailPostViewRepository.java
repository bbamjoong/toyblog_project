package klog.blog_project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import klog.blog_project.entity.Post;
import klog.blog_project.entity.QPost;
import klog.blog_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailPostViewRepository {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    public Optional<Post> findPost(User user, Long postId) {
        queryFactory = new JPAQueryFactory(em);
        QPost qPost = QPost.post;

        return Optional.ofNullable(queryFactory.selectFrom(qPost)
                .where(qPost.user.eq(user).and(qPost.postId.eq(postId)))
                .fetchOne());
    }
}
