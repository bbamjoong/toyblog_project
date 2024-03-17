package klog.blog_project.service;

import klog.blog_project.entity.User;
import klog.blog_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long join(User user) {
        validateUser(user);
        userRepository.save(user);
        return user.getUserId();
    }

    private void validateUser(User user) {
        validateDuplicateUserId(user);
        validateDuplicateNickname(user);
    }

    private void validateDuplicateUserId(User user) {
        User existingUserByUserId = userRepository.findByUserId(user.getUserId());
        if (existingUserByUserId != null) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    private void validateDuplicateNickname(User user) {
        User existingUserByNickname = userRepository.findByNickname(user.getNickname());
        if (existingUserByNickname != null) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }
    }
}
