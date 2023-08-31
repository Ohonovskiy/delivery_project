package application.pet.delivery.controllers.user.shops;

import application.pet.delivery.entities.Shop;
import application.pet.delivery.entities.User;
import application.pet.delivery.services.ProductService;
import application.pet.delivery.services.ShopService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shops")
@PreAuthorize("hasAuthority('user')")
public class ShopController {
    private final ShopService shopService;
    private final ProductService productService;
    private final UserService userService;
    private static Authentication authentication;
    private static User currentUser;
    @Autowired
    public ShopController(ShopService shopService, ProductService productService, UserService userService) {
        this.shopService = shopService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model){

        model.addAttribute("shops", shopService.getAll());

        return "shops/index";
    }

    @GetMapping("/{id}")
    public String shopPage(@PathVariable Long id, Model model){
        setCurrentUser();

        if(shopService.getById(id).isPresent()) {
            Shop shop = shopService.getById(id).get();

            model.addAttribute("user", currentUser);
            model.addAttribute("shop", shop);
            model.addAttribute("products", shop.getProducts());

            return "shops/shopPage";
        }

        return "shops/shopNotFound";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("productId") Long id,
                             @RequestParam(value = "shopId", required = false) Long shopId){
        setCurrentUser();

        currentUser.addProductToCart(productService.getById(id));
        userService.save(currentUser);

        if(shopId == null) shopId = 1L;

        return  "redirect:/shops/"+shopId;
    }

    @PostMapping("/removeProduct")
    public String removeProduct(@RequestParam("productId") Long id,
                                @RequestParam(value = "shopId", required = false) Long shopId){
        setCurrentUser();

        currentUser.removeProductFromCart(id);
        userService.save(currentUser);

        if(shopId == null) shopId = 1L;


        return  "redirect:/shops/"+shopId;
    }

    public boolean authenticationUserIsPresent(){
        setAuthentication();
        return userService.getByEmail(authentication.getName()).isPresent();
    }

    public void setAuthentication(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public void setCurrentUser(){
        setAuthentication();
        if(authenticationUserIsPresent())
            currentUser = userService.getByEmail(authentication.getName()).get();
    }
}
