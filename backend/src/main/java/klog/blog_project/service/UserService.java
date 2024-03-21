package klog.blog_project.service;

import static klog.blog_project.entity.Message.EXIST_ID;
import static klog.blog_project.entity.Message.EXIST_NICKNAME;

import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.UserDto;
import klog.blog_project.exception.UserDuplicateException;
import klog.blog_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    public Long signup(UserDto.SignupRequest dto) {
        validateUser(dto);
        return saveUser(dto);
    }

    @Transactional(readOnly = true)
    public void validateUser(UserDto.SignupRequest dto) {
        validateDuplicateUserId(dto);
        validateDuplicateNickname(dto);
    }

    @Transactional(readOnly = true)
    public void validateDuplicateUserId(UserDto.SignupRequest dto) {
        User existingUserById = userRepository.findById(dto.getId());
        if (existingUserById != null) {
            throw new UserDuplicateException(EXIST_ID.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public void validateDuplicateNickname(UserDto.SignupRequest dto) {
        User existingUserByNickname = userRepository.findByNickname(dto.getNickname());
        if (existingUserByNickname != null) {
            throw new UserDuplicateException(EXIST_NICKNAME.getMessage());
        }
    }
    
    private Long saveUser(UserDto.SignupRequest dto) {
        User user = User.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }
}
