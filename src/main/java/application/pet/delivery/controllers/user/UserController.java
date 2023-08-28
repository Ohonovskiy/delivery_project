package application.pet.delivery.controllers.user;

import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.User;
import application.pet.delivery.services.ProductService;
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
@PreAuthorize("hasAuthority('user')")
public class UserController {
    private final UserService userService;
    private final ProductService productService;

    private User currentUser;

    @Autowired
    public UserController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String index(){
        return "user/index";
    }

    @GetMapping("/profile")
    public String userPage(Model model){
        setCurrentUser();

        model.addAttribute("userProducts", currentUser.getProducts());
        model.addAttribute("user", currentUser);

        return "user/profile";
    }

    @GetMapping("/orders")
    public String userOrders(Model model){
        setCurrentUser();

        model.addAttribute("orders", currentUser.getOrders());

        return "user/orders";
    }

    @PostMapping("/placeOrder")
    public String placeOrder() {
        setCurrentUser();

        currentUser.placeOrder(new Order());

        userService.save(currentUser);

        return "redirect:/user/profile";
    }


    private void setCurrentUser(){
        currentUser = userService
                .getByEmail(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(() -> new RuntimeException("User with that email doesn't exist"));
    }
}
