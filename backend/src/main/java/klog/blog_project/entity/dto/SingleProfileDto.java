package klog.blog_project.entity.dto;

import klog.blog_project.entity.User;
import lombok.Data;

@Data
public class SingleProfileDto {
    private String profileImage;
    private String introductionTitle;
    private String introductionContent;

    public SingleProfileDto(User user) {
        this.profileImage = user.getProfileImage();
        this.introductionTitle = user.getIntroductionTitle();
        this.introductionContent = user.getIntroductionContent();
    }
}
