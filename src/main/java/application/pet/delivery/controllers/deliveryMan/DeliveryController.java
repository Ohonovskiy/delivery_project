package application.pet.delivery.controllers.deliveryMan;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.Product;
import application.pet.delivery.entities.googleMaps.MarkerInfo;
import application.pet.delivery.services.DeliveryManService;
import application.pet.delivery.services.GeoUtils.googleMapsApi.MarkerDataService;
import application.pet.delivery.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/delivery")
@PreAuthorize("hasAuthority('delivery_man')")
public class DeliveryController {
    private final OrderService orderService;
    private final DeliveryManService deliveryManService;
    private final MarkerDataService markerDataService;
    private DeliveryMan currentDeliveryMan;

    @Value("${GOOGLE_MAPS_KEY}")
    private String googleMapsApiKey;

    @Autowired
    public DeliveryController(OrderService orderService, DeliveryManService deliveryManService, MarkerDataService markerDataService) {
        this.orderService = orderService;
        this.deliveryManService = deliveryManService;
        this.markerDataService = markerDataService;
    }

    @GetMapping
    public String index(Model model){
        setCurrentDeliveryMan();

        model.addAttribute("markersData",
                markerDataService.generateMarkersForDeliveryman(currentDeliveryMan));
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);

        return "delivery/index";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        setCurrentDeliveryMan();

        model.addAttribute("hasOrder", currentDeliveryMan.hasOrder());
        model.addAttribute("order", currentDeliveryMan.getOrder());

        return "delivery/profile";
    }

    @GetMapping("/availableOrders")
    public String availableOrders(Model model) {
        setCurrentDeliveryMan();

        if(currentDeliveryMan.hasOrder())
            model.addAttribute("hasOrder", currentDeliveryMan.hasOrder());
         else {
            model.addAttribute("orderAndInfo", orderService.getOrderAndOrderInfoMap(currentDeliveryMan));
            model.addAttribute("hasOrder", currentDeliveryMan.hasOrder());
            System.out.println(orderService.getOrderAndOrderInfoMap(currentDeliveryMan).size());
        }

        return "delivery/orders";
    }

    @PostMapping("/takeOrder")
    public String takeOrder(@RequestParam("orderId") Long orderId){
        setCurrentDeliveryMan();

        Optional<Order> optionalOrder = orderService.getById(orderId);

        if(optionalOrder.isPresent()){
            if(optionalOrder.get().getDeliveryMan() != null)
                return "delivery/orderIsTaken";

            currentDeliveryMan.setOrder(optionalOrder.get());

            deliveryManService.save(currentDeliveryMan);
        }

        return "redirect:/delivery";
    }

    @PostMapping("/cancelDelivery")
    public String cancelOrder(){
        setCurrentDeliveryMan();

        currentDeliveryMan.removeOrder();
        deliveryManService.save(currentDeliveryMan);

        return "redirect:profile";
    }

    @PostMapping("/completeDelivery")
    public String completeDelivery(){
        setCurrentDeliveryMan();

        currentDeliveryMan.completeOrder();
        deliveryManService.save(currentDeliveryMan);

        return "redirect:profile";
    }

    private void setCurrentDeliveryMan(){
        currentDeliveryMan = deliveryManService
                .getByEmail(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName())
                .orElseThrow(() -> new RuntimeException("Delivery man not found"));
    }
}
