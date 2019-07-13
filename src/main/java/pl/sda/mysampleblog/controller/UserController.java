package pl.sda.mysampleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.mysampleblog.model.User;
import pl.sda.mysampleblog.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "register";
        }

        if (userService.passwordCheck(user.getPassword(), user.getPassword_confirm())) {
            userService.addNewUser(user);
            return "redirect:/";
        }
        model.addAttribute("passwordMessage", "Passwords do not match");
        return "register";

    }

    @GetMapping("/loginUser")
    public String login() {
        return "loginUser";
    }
}
