package application.pet.delivery.controllers;

import application.pet.delivery.DTO.UserDTO;
import application.pet.delivery.entities.User;
import application.pet.delivery.services.ProductService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class responsible for handling product-related operations and cart management.
 */
@Controller
@RequestMapping("/products")
@PreAuthorize("isAuthenticated()")
public class ProductsController {
    private final ProductService productService;
    private final UserService userService;

    /**
     * Constructs a new instance of ProductsController with the provided product and user services.
     *
     * @param productService An instance of the ProductService for managing product-related operations.
     * @param userService    An instance of the UserService for managing user-related operations.
     */
    @Autowired
    public ProductsController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    /**
     * Displays a list of products on the products index page.
     *
     * @param model The Model instance to add attributes for rendering in the view.
     * @return The name of the view template for the products index page.
     */
    @GetMapping
    public String products(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(userService.getByEmail(authentication.getName()).isPresent())
            model.addAttribute("role", userService.getByEmail(authentication.getName()).get().getRole());

        model.addAttribute("products", productService.getAll());

        return "products/index";
    }

    /**
     * Adds a product to the user's cart based on the provided product ID.
     *
     * @param id The ID of the product to be added to the cart.
     * @return A redirect URL to the products index page after adding the product to the cart.
     */
    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("productId") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;

        if(userService.getByEmail(authentication.getName()).isPresent()) {
            user = userService.getByEmail(authentication.getName()).get();
            user.addProductToCart(productService.getById(id));

            userService.save(user);
        }

        return "redirect:/products";
    }
}
