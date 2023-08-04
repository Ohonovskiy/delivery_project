package application.pet.delivery.controllers;

import application.pet.delivery.entities.User;
import application.pet.delivery.services.ManufacturerService;
import application.pet.delivery.services.ProductService;
import application.pet.delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@PreAuthorize("permitAll()")
public class testController {

    private final UserService userService;
    private final ManufacturerService manufacturerService;
    private final ProductService productService;

    @Autowired
    public testController(UserService userService, ManufacturerService manufacturerService, ProductService productService) {
        this.userService = userService;
        this.manufacturerService = manufacturerService;
        this.productService = productService;
    }

    @GetMapping("/1")
    @PreAuthorize("isAuthenticated()")
    public String test(){
        User user = userService.getByEmail("ostap@gmail.com").get();
        System.out.println(user.getRole().getAuthorities().toString());
        return "123";
    }
    @GetMapping("/2")
    @PreAuthorize("hasAuthority('developers:write')")
    public String test2(){

        return "123123";
    }
}
