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

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('developers:admin_page')")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

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

    @PostMapping("/users/ban")
    public String banUser(@ModelAttribute("id") Long id){
        userService.ban(id);
        System.out.println(id + " banned");
        return "redirect:/admin/users";
    }

    @PostMapping("/users/unban")
    public String unbanUser(@ModelAttribute("id") Long id){
        userService.unban(id);
        System.out.println(id + " unbanned");
        return "redirect:/admin/users";
    }
}
