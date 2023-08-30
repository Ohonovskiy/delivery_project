package application.pet.delivery.controllers;

import application.pet.delivery.services.DeliveryManService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final UserService userService;
    private final DeliveryManService deliveryManService;

    @Autowired
    public IndexController(UserService userService, DeliveryManService deliveryManService) {
        this.userService = userService;
        this.deliveryManService = deliveryManService;
    }

    @GetMapping
    public String index(Model model){

        model.addAttribute("isAuth", isAuth());

        return "universal/index";
    }

    @GetMapping("/about")
    public String about(Model model){

        model.addAttribute("isAuth", isAuth());

        return "universal/about";
    }

    @GetMapping("/contact")
    public String contact(Model model){

        model.addAttribute("isAuth", isAuth());

        return "universal/contact";
    }

    private boolean isAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByEmail(authentication.getName()).isPresent() || deliveryManService.getByEmail(authentication.getName()).isPresent();
    }
}
