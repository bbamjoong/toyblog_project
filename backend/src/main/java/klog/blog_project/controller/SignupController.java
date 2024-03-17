package klog.blog_project.controller;

import jakarta.validation.Valid;
import klog.blog_project.entity.User;
import klog.blog_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final UserService userService;

    @GetMapping("/signup")
    public String createForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "users/createUserForm";
    }

    @PostMapping("/signup")
    public String create(@Valid SignupForm form, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("hihi");
            System.out.println(result);

            return "users/createUserForm";
        }

        User user = User.builder()
                .id(form.getId())
                .password(form.getPassword())
                .nickname(form.getNickname())
                .build();

        userService.join(user);

        System.out.println("hi");
        return "redirect:/";
    }
}
