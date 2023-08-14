package application.pet.delivery.controllers.auth;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.User;
import application.pet.delivery.enums.Role;
import application.pet.delivery.services.DeliveryManService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final DeliveryManService deliveryManService;

    @Autowired
    public AuthController(UserService userService, DeliveryManService deliveryManService) {
        this.userService = userService;
        this.deliveryManService = deliveryManService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("birth", "");
        return "universal/authentication/registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @ModelAttribute("birth") String birthdayDate,
                               @RequestParam("latitude") Double latitude,
                               @RequestParam("longitude") Double longitude,
                               @RequestParam("role") Role role) throws ParseException {

        user.setBirthdayDate(new SimpleDateFormat("yyyy-MM-dd").parse(birthdayDate));
        user.setGeolocationX(longitude);
        user.setGeolocationY(latitude);
        user.setRole(role);

        if(role.equals(Role.ROLE_USER)) {
            userService.save(user);
        } else {
            DeliveryMan deliveryMan = userService.convertToDeliveryman(user);
            deliveryManService.save(deliveryMan);
        }


        return "redirect:/auth/success";
    }

    @GetMapping("/login")
    public String login(){
        return "universal/authentication/login";
    }

    @GetMapping("/success")
    public String success(){
        return "universal/authentication/registerMsg";
    }

    @GetMapping("/logout")
    public String logout(){
        return "universal/authentication/logout";
    }
}
