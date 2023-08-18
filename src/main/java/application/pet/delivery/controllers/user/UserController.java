package application.pet.delivery.controllers.user;

import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.User;
import application.pet.delivery.services.OrderService;
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

import java.util.ArrayList;

/**
 * Controller class for managing user-related operations and views.
 */
@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('user')")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private User currentUser;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String index(){
        return "user/index";
    }

    @GetMapping("/profile")
    public String userPage(Model model){
        setCurrentUser();

        model.addAttribute("userProducts", currentUser.getProducts());

        return "user/profile";
    }

    @GetMapping("/orders")
    public String userOrders(Model model){
        setCurrentUser();

        model.addAttribute("orders", currentUser.getOrders());

        return "user/orders";
    }

    @PostMapping("/removeProduct")
    public String removeProductFromCard(@ModelAttribute("productId") Long id){
        setCurrentUser();
        currentUser.removeProductFromCart(id);

        userService.save(currentUser);

        return "redirect:/user/profile";
    }

    @PostMapping("/placeOrder")
    public String placeOrder() {
        setCurrentUser();

        Order order = new Order();

        order.setUser(currentUser);
        order.setProducts(new ArrayList<>(currentUser.getProducts()));

        currentUser.removeAllProductsFromCart();

        orderService.save(order);
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
