package application.pet.delivery.controllers;

import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class responsible for handling the index page and displaying user information.
 */
@Controller
@PreAuthorize("permitAll()")
public class IndexController {
    private final UserService userService;

    /**
     * Constructs a new instance of IndexController with the provided user service.
     *
     * @param userService An instance of the UserService for managing user-related operations.
     */
    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays user information on the index page.
     *
     * @param model The Model instance to add attributes for rendering in the view.
     * @return The name of the view template for the index page.
     */
    @GetMapping
    public String whoAmI(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", authentication.getName());
        model.addAttribute("role", userService.getByEmail(
                authentication.getName()).get().getRole());

        return "index/index";
    }
}
