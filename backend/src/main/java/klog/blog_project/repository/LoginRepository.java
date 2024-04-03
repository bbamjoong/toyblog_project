package klog.blog_project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import klog.blog_project.entity.QUser;
import klog.blog_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class LoginRepository {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    public Optional<User> findUser(String id, String password) {
        queryFactory = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        return Optional.ofNullable(queryFactory.selectFrom(qUser)
                .where(qUser.id.eq(id).and(qUser.password.eq(password)))
                .fetchOne());
    }
}