package klog.blog_project.service;

import static klog.blog_project.entity.PostMessage.FORBIDDEN;
import static klog.blog_project.entity.PostMessage.NOT_EXIST_POST;

import java.util.Optional;
import klog.blog_project.entity.Post;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.PostDto;
import klog.blog_project.entity.dto.PostDto.ModifyPostRequest;
import klog.blog_project.exception.PostNotFoundException;
import klog.blog_project.exception.ForbiddenUserException;
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
        User user = userRepository.findByUserId(dto.getUserId());

        Post post = Post.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        postRepository.save(post);

        return post.getPostId();
    }

    public Post findPost(String nickname, Long postId) {
        User user = userRepository.findByNickname(nickname);
        if (user == null) {
            throw new PostNotFoundException(NOT_EXIST_POST.getMessage());
        }

        Optional<Post> postOptional = detailPostViewRepository.findPost(user, postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException(NOT_EXIST_POST.getMessage());
        }
        return postOptional.get();
    }

    public void modify(ModifyPostRequest dto, Long userId, Long postId) {
        // userId, postId를 검사했는데 없으면 not found
        User user = userRepository.findByUserId(userId);
        Optional<Post> postOptional = detailPostViewRepository.findPost(user, postId);
        if (postOptional.isEmpty()) {
            throw new PostNotFoundException(NOT_EXIST_POST.getMessage());
        }

        Post post = postOptional.get();
        // 글 수정
        post.changeInformation(dto.getTitle(), dto.getContent());
    }

    public void delete(Long userId, Long postId) {
        // querydsl 대신 JpaRepository 이용
        User user = userRepository.findByUserId(userId);
        Long deletedCount = postRepository.deletePostByPostIdAndUser(postId, user);
        if (deletedCount == 0) {
            throw new ForbiddenUserException(FORBIDDEN.getMessage());
        }
    }
}
