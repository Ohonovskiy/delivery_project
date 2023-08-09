package application.pet.delivery.controllers;

import application.pet.delivery.entities.User;
import application.pet.delivery.enums.Status;
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

import java.util.Optional;

/**
 * Controller class responsible for handling administrative actions related to user management and privileges.
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('developers:admin_page')")
public class AdminController {
    private final UserService userService;

    /**
     * Constructs a new instance of AdminController with the provided user service.
     *
     * @param userService An instance of the UserService for managing user-related operations.
     */
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of users and related information for display in the admin users page.
     *
     * @param model The Model instance to add attributes for rendering in the view.
     * @return The name of the view template for the admin users page.
     */
    @GetMapping("/users")
    public String users(Model model){
        Optional<User> currentUser = userService
                .getByEmail(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.getAll());
        model.addAttribute("statusActive", Status.ACTIVE);
        model.addAttribute("statusBanned", Status.BANNED);
        return "admin/users";
    }

    /**
     * Bans a user based on the provided user ID.
     *
     * @param id The ID of the user to be banned.
     * @return A redirect URL to the admin users page after performing the ban operation.
     */
    @PostMapping("/users/ban")
    public String banUser(@ModelAttribute("id") Long id){
        userService.ban(id);
        System.out.println(id + " banned");
        return "redirect:/admin/users";
    }

    /**
     * Unbans a previously banned user based on the provided user ID.
     *
     * @param id The ID of the user to be unbanned.
     * @return A redirect URL to the admin users page after performing the unban operation.
     */
    @PostMapping("/users/unban")
    public String unbanUser(@ModelAttribute("id") Long id){
        userService.unban(id);
        System.out.println(id + " unbanned");
        return "redirect:/admin/users";
    }
}
