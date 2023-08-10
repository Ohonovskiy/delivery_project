package application.pet.delivery.controllers;

import application.pet.delivery.entities.User;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Controller class responsible for handling user authentication and registration processes.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    /**
     * Constructs a new instance of AuthController with the provided user service.
     *
     * @param userService An instance of the UserService for managing user-related operations.
     */
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the registration form for new users.
     *
     * @param model The Model instance to add attributes for rendering in the view.
     * @return The name of the view template for the user registration page.
     */
    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("birth", "");
        return "authentication/registration";
    }

    /**
     * Handles the registration of a new user.
     *
     * @param user     The User object containing registration information.
     * @param birthdayDate    The birthdayDate string in "yyyy-MM-dd" format.
     * @param latitude The latitude value for user geolocation.
     * @param longitude The longitude value for user geolocation.
     * @return A redirect URL to the registration success page after successful registration.
     * @throws ParseException if there is an error parsing the birthdayDate string.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @ModelAttribute("birth") String birthdayDate,
                               @RequestParam("latitude") Double latitude,
                               @RequestParam("longitude") Double longitude) throws ParseException {

        user.setBirthdayDate(new SimpleDateFormat("yyyy-MM-dd").parse(birthdayDate));
        user.setGeolocationX(longitude);
        user.setGeolocationY(latitude);

        userService.save(user);

        return "redirect:/auth/success";
    }

    /**
     * Displays the login page for users.
     *
     * @return The name of the view template for the user login page.
     */
    @GetMapping("/login")
    public String login(){
        return "authentication/login";
    }

    /**
     * Displays the success page after successful registration.
     *
     * @return The name of the view template for the registration success page.
     */
    @GetMapping("/success")
    public String success(){
        return "authentication/registerMsg";
    }

    /**
     * Handles user logout.
     *
     * @return The name of the view template for the logout page.
     */
    @GetMapping("/logout")
    public String logout(){
        return "authentication/logout";
    }

}
