package application.pet.delivery.controllers;

import application.pet.delivery.services.ProductService;
import application.pet.delivery.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private final ProductService productService;

    @Autowired
    public ShopController(ShopService shopService, ProductService productService) {
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping("/1")
    public String test1(){
        return "shop/index";
    }
}
