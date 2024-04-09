package klog.blog_project.service;

import static klog.blog_project.entity.PostMessage.FORBIDDEN;
import static klog.blog_project.entity.UserMessage.EXIST_ID;
import static klog.blog_project.entity.UserMessage.EXIST_NICKNAME;
import static klog.blog_project.entity.UserMessage.NOT_EXIST_USER;

import java.util.List;
import java.util.Optional;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.ProfileDto.ModifyProfileRequest;
import klog.blog_project.entity.dto.SingleProfileDto;
import klog.blog_project.entity.dto.SingleSimpleProfileDto;
import klog.blog_project.entity.dto.UserDto;
import klog.blog_project.exception.ForbiddenUserException;
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

    public SingleProfileDto findUserProfileDto(String nickname) {
        Optional<SingleProfileDto> profileDtoOptional = userRepository.findUserDtoByNickname(nickname);

        if (profileDtoOptional.isEmpty()) {
            throw new UserNotFoundException(NOT_EXIST_USER.getMessage());
        }
        return profileDtoOptional.get();
    }

    public void modifyProfile(ModifyProfileRequest dto, Long userId, String nickname) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        // 세션의 userId는 반드시 존재하므로 get을 바로 이용
        User user = userOptional.get();

        // 로그인한 유저와, 프로필 수정할 URI의 닉네임 정보가 일치하지 않으면 Forbidden
        if (!user.getNickname().equals(nickname)) {
            throw new ForbiddenUserException(FORBIDDEN.getMessage());
        }
        user.changeInformation(dto);
    }

    public List<SingleSimpleProfileDto> findAllSimpleProfiles() {
        return userRepository.findAllSimpleProfiles();
    }
}
