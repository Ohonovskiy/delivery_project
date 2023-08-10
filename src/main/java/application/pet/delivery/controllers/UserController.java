package application.pet.delivery.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing user-related operations and views.
 */
@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserController {
    @GetMapping
    public String userPage(){
        return "user/index";
    }
}
