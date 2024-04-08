package klog.blog_project.service;

import klog.blog_project.entity.Post;
import klog.blog_project.entity.User;
import klog.blog_project.entity.dto.PostDto;
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

    public Long write(PostDto.WriteRequest dto) {
        System.out.println("유저를 찾도록 합니다.");
        User user = userRepository.findByUserId(dto.getUserId());
        System.out.println("유저를 찾았다.");
        Post post = Post.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        System.out.println("포스트 저장할거임");
        postRepository.save(post);
        System.out.println("포스트 저장완료");
        return post.getPostId();
    }
}
