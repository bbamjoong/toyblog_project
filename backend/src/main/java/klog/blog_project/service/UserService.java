package klog.blog_project.service;

import static klog.blog_project.entity.UserMessage.EXIST_ID;
import static klog.blog_project.entity.UserMessage.EXIST_NICKNAME;
import static klog.blog_project.entity.UserMessage.NOT_EXIST_USER;

import java.util.Optional;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.UserDto;
import klog.blog_project.exception.UserDuplicateException;
import klog.blog_project.exception.UserNotFoundException;
import klog.blog_project.repository.LoginRepository;
import klog.blog_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

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
        Optional<User> existingUserById = userRepository.findById(dto.getId());
        if (existingUserById.isPresent()) {
            throw new UserDuplicateException(EXIST_ID.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public void validateDuplicateNickname(UserDto.SignupRequest dto) {
        Optional<User> existingUserByNickname = userRepository.findByNickname(dto.getNickname());
        if (existingUserByNickname.isPresent()) {
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

    @Transactional(readOnly = true)
    public Long login(UserDto.LoginRequest dto) {
        Optional<User> userOptional = loginRepository.findUser(dto.getId(), dto.getPassword());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(NOT_EXIST_USER.getMessage());
        }
        return userOptional.get().getUserId();
    }
}
