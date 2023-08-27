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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        if(userService.getByEmail(email).isPresent()){
            model.addAttribute("isAuth", true);
            return "universal/index";
        } else if (deliveryManService.getByEmail(email).isPresent()) {
            return "redirect:/delivery";
        }

        model.addAttribute("isAuth", false);

        return "universal/index";
    }
}
