package klog.blog_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import klog.blog_project.entity.dto.ProfileDto.ModifyProfileRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission_id")
    private Permission permission;

    private String id;

    private String password;

    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "introduction_title")
    private String introductionTitle;

    @Column(name = "introduction_content")
    private String introductionContent;

    @Builder
    public User(String id, String password, String nickname) {
        this.permission = Permission.Member;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = "https://avatars.githubusercontent.com/u/121084350?v=4";
        this.introductionTitle = "안녕하세요";
        this.introductionContent = "반갑습니다";
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public void changeInformation(ModifyProfileRequest dto) {
        this.profileImage = dto.getProfileImage();
        this.introductionTitle = dto.getIntroductionTitle();
        this.introductionContent = dto.getIntroductionContent();
    }
}
