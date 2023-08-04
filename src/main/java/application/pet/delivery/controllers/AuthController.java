package application.pet.delivery.controllers;

import application.pet.delivery.entities.User;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("birth", "");
        return "authentication/registration";

    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @ModelAttribute("birth") String birth) throws ParseException {

        user.setBirthdayDate(new SimpleDateFormat("yyyy-MM-dd").parse(birth));
        //userService.save(user);
        return "redirect:/auth/success";
    }

    @GetMapping("/login")
    public String login(){
        return "authentication/login";
    }

    @GetMapping("/success")
    public String success(){
        return "authentication/success";
    }

    @GetMapping("/testGeo")
    public String test(){
        return "authentication/testGeo";
    }

    @PostMapping("/geo")
    public String test2(@RequestParam("latitude") Double latitude,
                        @RequestParam("longitude") Double longitude){
        System.out.println(latitude);
        System.out.println(longitude);
        return "authentication/testGeo";
    }

}
