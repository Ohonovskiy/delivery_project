package application.pet.delivery.controllers.user;

import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.User;
import application.pet.delivery.services.OrderService;
import application.pet.delivery.services.ProductService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing user-related operations and views.
 */
@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('user')")
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    private User currentUser;

    @Autowired
    public UserController(UserService userService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping
    public String index(){
        return "redirect:/user/profile";
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

        List<Order> orders = currentUser.getOrders();

        orders.forEach(o -> o.setPrice(orderService.calculateOrderPrice(o)));

        orders.sort(Comparator.comparing(Order::getPlaceTime, Comparator.nullsLast(Comparator.reverseOrder())));

        model.addAttribute("orders", orders);
        model.addAttribute("user", currentUser);


        return "user/orders";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude) {
        setCurrentUser();

        if(latitude != null && longitude != null){
            currentUser.setGeolocationLatitude(latitude);
            currentUser.setGeolocationLongitude(longitude);
        }

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
