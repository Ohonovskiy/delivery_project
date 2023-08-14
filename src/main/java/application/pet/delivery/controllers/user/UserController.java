package application.pet.delivery.controllers.user;

import application.pet.delivery.entities.User;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing user-related operations and views.
 */
@Controller
@RequestMapping("/user")
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final UserService userService;
    private User currentUser;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userPage(Model model){
        setCurrentUser();

        model.addAttribute("userProducts", currentUser.getProducts());

        return "user/profile";
    }

    @PostMapping("/removeProduct")
    public String removeProductFromCard(@ModelAttribute("productId") Long id){
        setCurrentUser();
        currentUser.removeProductFromCart(id);

        userService.save(currentUser);
        return "redirect:/user";
    }

    private void setCurrentUser(){
        currentUser = userService
                .getByEmail(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()).get();
    }
}
