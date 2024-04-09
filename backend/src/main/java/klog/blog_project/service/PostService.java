package klog.blog_project.service;

import static klog.blog_project.entity.PostMessage.FORBIDDEN;
import static klog.blog_project.entity.PostMessage.NOT_EXIST_POST;
import static klog.blog_project.entity.UserMessage.NOT_EXIST_USER;

import java.util.List;
import java.util.Optional;
import klog.blog_project.entity.Post;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.PostDto;
import klog.blog_project.entity.dto.PostDto.ModifyPostRequest;
import klog.blog_project.entity.dto.SinglePostDto;
import klog.blog_project.exception.ForbiddenUserException;
import klog.blog_project.exception.PostNotFoundException;
import klog.blog_project.exception.UserNotFoundException;
import klog.blog_project.repository.DetailPostViewRepository;
import klog.blog_project.repository.PostRepository;
import klog.blog_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final DetailPostViewRepository detailPostViewRepository;

    public Long write(PostDto.WriteRequest dto) {
        Optional<User> userOptional = userRepository.findByUserId(dto.getUserId());

        // session의 userId는 반드시 존재
        Post post = Post.builder()
                .user(userOptional.get())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        postRepository.save(post);

        return post.getPostId();
    }

    public Post findPost(String nickname, Long postId) {
        Optional<User> userOptional = userRepository.findByNickname(nickname);
        if (userOptional.isEmpty()) {
            throw new PostNotFoundException(NOT_EXIST_POST.getMessage());
        }
        User user = userOptional.get();

        Optional<Post> postOptional = detailPostViewRepository.findPost(user, postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException(NOT_EXIST_POST.getMessage());
        }
        return postOptional.get();
    }

    public void modify(ModifyPostRequest dto, Long userId, Long postId) {
        // userId, postId를 검사했는데 없으면 not found
        Optional<User> userOptional = userRepository.findByUserId(userId);
        // 세션의 userId는 반드시 존재하므로 get을 바로 이용
        Optional<Post> postOptional = detailPostViewRepository.findPost(userOptional.get(), postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException(NOT_EXIST_POST.getMessage());
        }

        Post post = postOptional.get();
        // 글 수정
        post.changeInformation(dto.getTitle(), dto.getContent());
    }

    public void delete(Long userId, Long postId) {
        // querydsl 대신 JpaRepository 이용
        Optional<User> user = userRepository.findByUserId(userId);
        // 세션의 userId는 반드시 존재하므로 get을 바로 이용
        Long deletedCount = postRepository.deletePostByPostIdAndUser(postId, user.get());
        if (deletedCount == 0) {
            throw new ForbiddenUserException(FORBIDDEN.getMessage());
        }
    }

    public List<SinglePostDto> findAllPosts(String nickname) {
        // 닉네임이 없으면 not found
        Optional<User> userOptional = userRepository.findByNickname(nickname);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(NOT_EXIST_USER.getMessage());
        }

        User user = userOptional.get();
        return postRepository.findPostsByUser(user);
    }
}
