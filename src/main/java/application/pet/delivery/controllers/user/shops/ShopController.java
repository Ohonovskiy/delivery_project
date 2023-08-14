package application.pet.delivery.controllers.user.shops;

import application.pet.delivery.entities.Shop;
import application.pet.delivery.entities.User;
import application.pet.delivery.services.ManufacturerService;
import application.pet.delivery.services.ProductService;
import application.pet.delivery.services.ShopService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;
    private final ProductService productService;
    private final UserService userService;
    private final ManufacturerService manufacturerService;

    @Autowired
    public ShopController(ShopService shopService, ProductService productService, UserService userService, ManufacturerService manufacturerService) {
        this.shopService = shopService;
        this.productService = productService;
        this.userService = userService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("shops", shopService.getAll());

        return "shops/index";
    }

    @GetMapping("/{id}")
    public String shopPage(@PathVariable Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = null;

        if(shopService.getById(id).isPresent()) {
            Shop shop = shopService.getById(id).get();

            if(userService.getByEmail(authentication.getName()).isPresent()){
                currentUser = userService.getByEmail(authentication.getName()).get();
                model.addAttribute("userProducts", currentUser.getProducts());
            }

            model.addAttribute("user", currentUser);
            model.addAttribute("shop", shop);
            model.addAttribute("products", shop.getProducts());

            return "shops/shopPage";
        }

        return "shops/shopNotFound";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("productId") Long id,
                             @ModelAttribute("shopId") Long shopId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(userService.getByEmail(authentication.getName()).isPresent()) {

            User user;
            user = userService.getByEmail(authentication.getName()).get();
            user.addProductToCart(productService.getById(id));

            userService.save(user);
        }

        return "redirect:/shops/" + shopId;
    }

    @PostMapping("/removeProduct")
    public String removeProduct(@ModelAttribute("productId") Long id,
                                @ModelAttribute("shopId") Long shopId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(userService.getByEmail(authentication.getName()).isPresent()) {
            User user;
            user = userService.getByEmail(authentication.getName()).get();
            user.removeProductFromCart(id);

            userService.save(user);
        }

        return "redirect:/shops/" + shopId;
    }
}
