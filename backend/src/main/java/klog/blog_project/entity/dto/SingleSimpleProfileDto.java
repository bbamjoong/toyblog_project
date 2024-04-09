package klog.blog_project.entity.dto;

import klog.blog_project.entity.User;
import lombok.Data;

@Data
public class SingleSimpleProfileDto {
    private String nickname;
    private String profileImage;

    public SingleSimpleProfileDto(User user) {
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
